package com.digital.assessment.order.service;

import com.digital.assessment.order.domain.Branch;
import com.digital.assessment.order.web.rest.request.branch.BranchCreateRequest;
import com.digital.assessment.order.web.rest.request.branch.BranchUpdateRequest;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;

import java.util.List;
import java.util.UUID;

public interface BranchService {
    Branch create(BranchCreateRequest request);
    Branch update(BranchUpdateRequest request, UUID branchId);
    Void delete(UUID branchId);
    PageResponse<Branch> searchBranch(PageCriteria pageCriteria);
    List<Branch> searchNearestBranch(double latitude, double longitude, double distance);
}
