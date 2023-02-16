package com.digital.assessment.order.service.impl;

import com.digital.assessment.order.domain.Queue;
import com.digital.assessment.order.entity.QueueEntity;
import com.digital.assessment.order.enums.StatusOrder;
import com.digital.assessment.order.enums.StatusQueue;
import com.digital.assessment.order.exception.http.InvalidInputError;
import com.digital.assessment.order.exception.http.NotFoundError;
import com.digital.assessment.order.repository.OrderRepository;
import com.digital.assessment.order.repository.QueueRepository;
import com.digital.assessment.order.service.QueueService;
import com.digital.assessment.order.service.mapper.QueueMapper;
import com.digital.assessment.order.web.rest.request.queue.QueueCreateRequest;
import com.digital.assessment.order.web.rest.request.queue.QueueUpdateRequest;
import com.vsm.vin.common.misc.exception.http.ResponseException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class QueueServiceImpl implements QueueService {
    private final QueueRepository queueRepository;
    private final QueueMapper queueMapper;
    private final OrderRepository orderRepository;

    public QueueServiceImpl(QueueRepository queueRepository, QueueMapper queueMapper, OrderRepository orderRepository) {
        this.queueRepository = queueRepository;
        this.queueMapper = queueMapper;
        this.orderRepository = orderRepository;
    }

    @Override
    @Transactional
    public Queue create(UUID branchId, QueueCreateRequest request) {
        var existQueue = queueRepository.findByQueueCode(branchId, request.getQueueCode());
        if (existQueue.isPresent()) {
            throw new ResponseException(InvalidInputError.QUEUE_EXIST.getMessage(), InvalidInputError.QUEUE_EXIST);
        }
        var queueEntity = QueueEntity.builder()
                .queueId(UUID.randomUUID())
                .branchId(branchId)
                .queueCode(request.getQueueCode())
                .size(request.getSize())
                .maxCount(request.getMaxCount())
                .levelCount(1)
                .status(StatusQueue.ACTIVE)
                .build();
        queueRepository.save(queueEntity);
        return queueMapper.toTarget(queueEntity);
    }

    @Override
    @Transactional
    public Queue update(UUID branchId, UUID queueId, QueueUpdateRequest request) {
        var queueEntity = this.findByQueueId(branchId, queueId);
        if (Objects.nonNull(request.getSize())) {
            queueEntity.setSize(request.getSize());
        }
        if (Objects.nonNull(request.getMaxCount())) {
            queueEntity.setMaxCount(request.getMaxCount());
        }
        if (Objects.nonNull(request.getStatus())) {
            if (request.getStatus().equals(StatusQueue.INACTIVE)) {
                // checking order exist in queue before inactive
                var queueOrders = orderRepository.findNewOrProcessOrderInQueue(branchId, queueId, StatusOrder.NEW, StatusOrder.PROCESSING);
                if (!CollectionUtils.isEmpty(queueOrders)) {
                    throw new ResponseException(InvalidInputError.CANNOT_INACTIVE_QUEUE.getMessage(),
                            InvalidInputError.CANNOT_INACTIVE_QUEUE);
                }
            }
            queueEntity.setStatus(request.getStatus());
        }
        queueRepository.save(queueEntity);
        return queueMapper.toTarget(queueEntity);
    }

    @Override
    public Queue getDetail(UUID branchId, UUID queueId) {
        var queueEntity = this.findByQueueId(branchId, queueId);
        return queueMapper.toTarget(queueEntity);
    }

    @Override
    public List<Queue> searchQueue(UUID branchId) {
        var queueList = queueRepository.findAllByBranch(branchId);
        if (!CollectionUtils.isEmpty(queueList)) {
            return queueMapper.toTarget(queueList);
        }
        return new ArrayList<>();
    }
    
    public QueueEntity findByQueueId(UUID branchId, UUID queueId) {
        var existQueue = queueRepository.findByQueueId(branchId, queueId);
        if (existQueue.isEmpty()) {
            throw new ResponseException(NotFoundError.QUEUE_NOT_FOUND.getMessage(), NotFoundError.QUEUE_NOT_FOUND);
        }
        return existQueue.get();
    }
}
