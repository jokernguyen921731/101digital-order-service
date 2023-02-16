package com.digital.assessment.order.web.rest.impl;

import com.digital.assessment.order.domain.Order;
import com.digital.assessment.order.service.OrderService;
import com.digital.assessment.order.web.rest.OrderResource;
import com.digital.assessment.order.web.rest.request.order.OrderCreateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class OrderResourceImpl implements OrderResource {
    private final OrderService orderService;

    public OrderResourceImpl(OrderService orderService) {
        this.orderService = orderService;
    }

    @Override
    public ServiceResponse<Order> create(UUID branchId, OrderCreateRequest request) {
        return ServiceResponse.succeed(HttpStatus.OK, orderService.create(branchId, request));
    }

    @Override
    public ServiceResponse<Order> getDetail(UUID branchId, UUID orderId) {
        return ServiceResponse.succeed(HttpStatus.OK, orderService.getDetail(branchId, orderId));
    }

    @Override
    public ServiceResponse<Void> take(UUID branchId, UUID orderId) {
        return ServiceResponse.succeed(HttpStatus.OK, orderService.take(branchId, orderId));
    }

    @Override
    public ServiceResponse<Void> cancel(UUID branchId, UUID orderId) {
        return ServiceResponse.succeed(HttpStatus.OK, orderService.cancel(branchId, orderId));
    }

    @Override
    public ServiceResponse<List<Order>> search(UUID branchId, UUID queueId) {
        return ServiceResponse.succeed(HttpStatus.OK, orderService.search(branchId, queueId));
    }
}
