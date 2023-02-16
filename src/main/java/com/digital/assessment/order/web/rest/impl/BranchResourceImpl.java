package com.digital.assessment.order.web.rest.impl;

import com.digital.assessment.order.domain.Branch;
import com.digital.assessment.order.service.BranchService;
import com.digital.assessment.order.web.rest.BranchResource;
import com.digital.assessment.order.web.rest.request.branch.BranchCreateRequest;
import com.digital.assessment.order.web.rest.request.branch.BranchUpdateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@Slf4j
public class BranchResourceImpl implements BranchResource {
    private final BranchService service;

    public BranchResourceImpl(BranchService service) {
        this.service = service;
    }

    @Override
    public ServiceResponse<Branch> create(BranchCreateRequest request) {
        return ServiceResponse.succeed(HttpStatus.OK, service.create(request));
    }

    @Override
    public ServiceResponse<Branch> update(BranchUpdateRequest request, UUID branchId) {
        return ServiceResponse.succeed(HttpStatus.OK, service.update(request, branchId));
    }

    @Override
    public ServiceResponse<Void> delete(UUID branchId) {
        return ServiceResponse.succeed(HttpStatus.OK,service.delete(branchId));
    }

    @Override
    public ServiceResponse<PageResponse<Branch>> searchBranch(PageCriteria pageCriteria) {
        return ServiceResponse.succeed(HttpStatus.OK,service.searchBranch(pageCriteria));
    }

    @Override
    public ServiceResponse<List<Branch>> searchNearestBranch(double latitude, double longitude, double distance) {
        return ServiceResponse.succeed(HttpStatus.OK,service.searchNearestBranch(latitude, longitude, distance));
    }
}
