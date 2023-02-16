package com.digital.assessment.order.service;

import com.digital.assessment.order.domain.Queue;
import com.digital.assessment.order.web.rest.request.queue.QueueCreateRequest;
import com.digital.assessment.order.web.rest.request.queue.QueueUpdateRequest;

import java.util.List;
import java.util.UUID;

public interface QueueService {
    Queue create(UUID branchId, QueueCreateRequest request);
    Queue update(UUID branchId, UUID queueId, QueueUpdateRequest request);
    Queue getDetail(UUID branchId, UUID queueId);
    List<Queue> searchQueue(UUID branchId);
}
