package com.digital.assessment.order.web.rest.impl;

import com.digital.assessment.order.domain.Queue;
import com.digital.assessment.order.service.QueueService;
import com.digital.assessment.order.web.rest.QueueResource;
import com.digital.assessment.order.web.rest.request.queue.QueueCreateRequest;
import com.digital.assessment.order.web.rest.request.queue.QueueUpdateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class QueueResourceImpl implements QueueResource {
    private final QueueService service;

    public QueueResourceImpl(QueueService service) {
        this.service = service;
    }

    @Override
    public ServiceResponse<Queue> create(UUID branchId, QueueCreateRequest request) {
        return ServiceResponse.succeed(HttpStatus.OK, service.create(branchId, request));
    }

    @Override
    public ServiceResponse<Queue> update(UUID branchId, UUID queueId, QueueUpdateRequest request) {
        return ServiceResponse.succeed(HttpStatus.OK, service.update(branchId, queueId, request));
    }

    @Override
    public ServiceResponse<List<Queue>> search(UUID branchId) {
        return ServiceResponse.succeed(HttpStatus.OK, service.searchQueue(branchId));
    }

    @Override
    public ServiceResponse<Queue> getDetail(UUID branchId, UUID queueId) {
        return ServiceResponse.succeed(HttpStatus.OK, service.getDetail(branchId, queueId));
    }
}
