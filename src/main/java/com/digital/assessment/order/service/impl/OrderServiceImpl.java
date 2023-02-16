package com.digital.assessment.order.service.impl;

import com.digital.assessment.order.domain.Order;
import com.digital.assessment.order.entity.*;
import com.digital.assessment.order.enums.StatusOrder;
import com.digital.assessment.order.exception.http.InvalidInputError;
import com.digital.assessment.order.exception.http.NotFoundError;
import com.digital.assessment.order.repository.*;
import com.digital.assessment.order.service.OrderService;
import com.digital.assessment.order.service.mapper.*;
import com.digital.assessment.order.util.Const;
import com.digital.assessment.order.web.rest.request.order.OrderCreateRequest;
import com.vsm.vin.common.misc.exception.http.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final MenuRepository menuRepository;
    private final QueueRepository queueRepository;
    private final UserRepository userRepository;
    private final UserMenuRepository userMenuRepository;
    private final UserBranchRepository userBranchRepository;
    private final BranchServiceImpl branchService;
    private final QueueServiceImpl queueService;
    private final OrderMapper orderMapper;
    private final BranchMapper branchMapper;
    private final MenuMapper menuMapper;
    private final UserMapper userMapper;
    private final QueueMapper queueMapper;

    public OrderServiceImpl(OrderRepository orderRepository,
                            MenuRepository menuRepository,
                            QueueRepository queueRepository,
                            UserMenuRepository userMenuRepository,
                            UserRepository userRepository,
                            UserBranchRepository userBranchRepository,
                            BranchServiceImpl branchService,
                            QueueServiceImpl queueService, OrderMapper orderMapper, BranchMapper branchMapper, MenuMapper menuMapper, UserMapper userMapper, QueueMapper queueMapper) {
        this.orderRepository = orderRepository;
        this.menuRepository = menuRepository;
        this.queueRepository = queueRepository;
        this.userMenuRepository = userMenuRepository;
        this.userRepository = userRepository;
        this.userBranchRepository = userBranchRepository;
        this.branchService = branchService;
        this.queueService = queueService;
        this.orderMapper = orderMapper;
        this.branchMapper = branchMapper;
        this.menuMapper = menuMapper;
        this.userMapper = userMapper;
        this.queueMapper = queueMapper;
    }

    @Override
    @Transactional
    public Order create(UUID branchId, OrderCreateRequest request) {
        log.info("----------Start creating an Order ----------");
        var branchEntity = branchService.findByBranchId(branchId);
        var menuEntities = menuRepository.findByMenuIds(branchId, request.getMenuId());
        if (menuEntities.isEmpty() || menuEntities.size() != request.getMenuId().size()) {
            throw new ResponseException(NotFoundError.MENU_NOT_FOUND.getMessage(), NotFoundError.MENU_NOT_FOUND);
        }
        var queueEntity = queueService.findByQueueId(branchId, request.getQueueId());
        AtomicInteger currentCost = new AtomicInteger();
        List<UserMenuEntity> userMenuEntities = new ArrayList<>();
        var caller = this.getCurrentUser(request.getUserId());
        var menuIds = menuEntities.stream().map(MenuEntity::getMenuId).collect(Collectors.toSet());
        var userMenuDataEntities = userMenuRepository.findByMenuIds(caller.getUserId(), menuIds);
        var mapUserMenu = userMenuDataEntities.stream()
                .collect(Collectors.toMap(UserMenuEntity::getMenuId, o->o));
        log.info("----------Caller {} - Branch location name {} - Queue code {} - list selected item name {}----------",
                caller.getFullName(), branchEntity.getLocationName(), queueEntity.getQueueCode(),
                menuEntities.stream().map(MenuEntity::getName).collect(Collectors.toSet()));

        // check current size in queue
        var queueOrderEntities = orderRepository.findNewOrProcessOrderInQueue(branchId,
                request.getQueueId(), StatusOrder.NEW, StatusOrder.PROCESSING);
        if (queueOrderEntities.size() >= queueEntity.getSize()) {
            throw new ResponseException(InvalidInputError.QUEUE_SIZE_OVER.getMessage(), InvalidInputError.QUEUE_SIZE_OVER);
        }
        log.info("----------Check size of queue {} - order not done in queue {} ----------",
                queueEntity.getSize(), queueOrderEntities.size());

        // calculate sequence number
        var currentCount = queueEntity.getCount() + 1;
        var level = queueEntity.getLevelCount();
        if (currentCount > queueEntity.getMaxCount()) {
            level++;
            currentCount = 1;
        }

        log.info("----------level in queue {} - index in queue {}----------", level, currentCount);

        // get currentCost
        menuEntities.forEach(menu -> {
            currentCost.addAndGet(menu.getPrice());
            // increase item count
            var userMenuEntity = UserMenuEntity.builder()
                    .id(UUID.randomUUID())
                    .userId(caller.getUserId())
                    .menuId(menu.getMenuId())
                    .itemCount(1)
                    .build();
            if (mapUserMenu.containsKey(menu.getMenuId())) {
                userMenuEntity.setId(mapUserMenu.get(menu.getMenuId()).getId());
                userMenuEntity.setItemCount(mapUserMenu.get(menu.getMenuId()).getItemCount()+1);
            }
            userMenuEntities.add(userMenuEntity);
        });
        // check total cost
        if (currentCost.get() != request.getTotalCost()) {
            throw new ResponseException(InvalidInputError.INVALID_TOTAL_COST.getMessage(), InvalidInputError.INVALID_TOTAL_COST);
        }
        log.info("----------total cost from client {} - calculated cost from list item {}----------",
                request.getTotalCost(), currentCost.get());

        var orderEntity = OrderEntity.builder()
                .orderId(UUID.randomUUID())
                .branchId(branchId)
                .queueId(request.getQueueId())
                .customerId(caller.getUserId())
                .sequenceLevel(level)
                .sequenceNumber(currentCount)
                .status(StatusOrder.NEW)
                .note(request.getNote())
                .statusPayment(request.getStatusPayment())
                .totalCost(request.getTotalCost())
                .moneyType(request.getMoneyType())
                .menuEntities(menuEntities)
                .build();

        // increase branch served time
        var userBranchEntity = UserBranchEntity.builder()
                .id(UUID.randomUUID())
                .userId(caller.getUserId())
                .branchId(branchId)
                .servedTime(1)
                .build();
        var existUserBranch = userBranchRepository.findByBranchId(branchId, caller.getUserId());
        existUserBranch.ifPresent(entity -> {
            userBranchEntity.setId(entity.getId());
            userBranchEntity.setServedTime(entity.getServedTime() + 1);
        });
        log.info("----------user data {} - served time {} - payment status {}----------",
                caller.getFullName(), userBranchEntity.getServedTime(), request.getStatusPayment());

        // save new sequence to queue
        queueEntity.setCount(currentCount);
        queueEntity.setLevelCount(level);
        queueRepository.save(queueEntity);
        orderRepository.save(orderEntity);
        userMenuRepository.saveAll(userMenuEntities);
        userBranchRepository.save(userBranchEntity);
        var order = orderMapper.toTarget(orderEntity);
        this.enrichOrder(orderEntity, order, menuEntities);
        return order;
    }

    @Override
    @Transactional
    public Order getDetail(UUID branchId, UUID orderId) {
        branchService.findByBranchId(branchId);
        var orderEntity = this.findByOrderId(branchId, orderId);
        var orderEntities = orderRepository.findNewOrProcessOrderInQueue(branchId,
                orderEntity.getQueueId(), StatusOrder.NEW, StatusOrder.PROCESSING);
        var order = orderMapper.toTarget(orderEntity);
        this.enrichOrder(orderEntity, order, orderEntity.getMenuEntities());
        order.setIndexInQueue(orderEntities.indexOf(orderEntity));
        return order;
    }

    @Override
    @Transactional
    public Void take(UUID branchId, UUID orderId) {
        branchService.findByBranchId(branchId);
        var orderEntity = this.findByOrderId(branchId, orderId);
        if (orderEntity.getStatus() == StatusOrder.DONE || orderEntity.getStatus() == StatusOrder.CANCEL) {
            throw new ResponseException(InvalidInputError.CANNOT_TAKE_ORDER.getMessage(), InvalidInputError.CANNOT_TAKE_ORDER);
        }
        orderEntity.setStatus(StatusOrder.DONE);
        orderRepository.save(orderEntity);
        return null;
    }

    @Override
    @Transactional
    public Void cancel(UUID branchId, UUID orderId) {
        branchService.findByBranchId(branchId);
        var orderEntity = this.findByOrderId(branchId, orderId);
        if (orderEntity.getStatus() == StatusOrder.DONE || orderEntity.getStatus() == StatusOrder.CANCEL) {
            throw new ResponseException(InvalidInputError.CANNOT_CANCEL_ORDER.getMessage(), InvalidInputError.CANNOT_CANCEL_ORDER);
        }
        orderEntity.setStatus(StatusOrder.CANCEL);
        orderRepository.save(orderEntity);
        return null;
    }

    @Override
    @Transactional
    public List<Order> search(UUID branchId, UUID queueId) {
        List<Order> orders = new ArrayList<>();
        branchService.findByBranchId(branchId);
        queueService.findByQueueId(branchId, queueId);
        var orderEntities = orderRepository.findNewOrProcessOrderInQueue(branchId, queueId, StatusOrder.NEW, StatusOrder.PROCESSING);
        orderEntities.forEach(orderEntity -> {
            var order = orderMapper.toTarget(orderEntity);
            enrichOrder(orderEntity, order, orderEntity.getMenuEntities());
            orders.add(order);
        });
        return orders;
    }

    private UserEntity getCurrentUser(UUID userId) {
        var user = userRepository.findByUserId(userId);
        if (user.isEmpty()) {
            throw new ResponseException(NotFoundError.USER_NOT_FOUND.getMessage(), NotFoundError.USER_NOT_FOUND);
        }
        return user.get();
    }

    public OrderEntity findByOrderId(UUID branchId, UUID orderId) {
        var existOrder = orderRepository.findByOrderId(branchId, orderId);
        if (existOrder.isEmpty()) {
            throw new ResponseException(NotFoundError.ORDER_NOT_FOUND.getMessage(), NotFoundError.ORDER_NOT_FOUND);
        }
        return existOrder.get();
    }

    public void enrichOrder(OrderEntity orderEntity, Order order,List<MenuEntity> menuEntities) {
        if (Objects.nonNull(orderEntity) && Objects.nonNull(order)) {
            var userEntity = this.getCurrentUser(orderEntity.getCustomerId());
            var branchEntity = branchService.findByBranchId(orderEntity.getBranchId());
            var queueEntity = queueService.findByQueueId(orderEntity.getBranchId(), orderEntity.getQueueId());
            order.setSequence(orderEntity.getSequenceLevel() + Const.SEQUENCE_JOIN_KEY + orderEntity.getSequenceNumber());
            order.setUser(userMapper.toTarget(userEntity));
            order.setMenu(menuMapper.toTarget(menuEntities));
            order.setBranch(branchMapper.toTarget(branchEntity));
            order.setQueue(queueMapper.toTarget(queueEntity));
        }
    }
}
