package com.digital.assessment.order.service;

import com.digital.assessment.order.domain.Order;
import com.digital.assessment.order.web.rest.request.order.OrderCreateRequest;

import java.util.List;
import java.util.UUID;

public interface OrderService {
    Order create(UUID branchId, OrderCreateRequest request);
    Order getDetail(UUID branchId, UUID orderId);
    Void take(UUID branchId, UUID orderId);
    Void cancel(UUID branchId, UUID orderId);
    List<Order> search(UUID branchId, UUID queueId);
}
