package com.digital.assessment.order.web.rest;

import com.digital.assessment.order.domain.Branch;
import com.digital.assessment.order.web.rest.request.branch.BranchCreateRequest;
import com.digital.assessment.order.web.rest.request.branch.BranchUpdateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import com.digital.assessment.order.web.rest.validator.ValidatePageCriteria;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@Api(tags = "Branch REST Resource")
@RequestMapping("/branch")
@Validated
public interface BranchResource {
    @ApiOperation(value = "Create a branch")
    @PostMapping("/create")
    ServiceResponse<Branch> create(@RequestBody @Valid BranchCreateRequest request);

    @ApiOperation(value = "update a branch")
    @PutMapping("/update/{branch-id}")
    ServiceResponse<Branch> update(@RequestBody @Valid BranchUpdateRequest request,
                                   @PathVariable("branch-id") @Valid UUID branchId);

    @ApiOperation(value = "delete a branch")
    @DeleteMapping("/delete/{branch-id}")
    ServiceResponse<Void> delete(@PathVariable("branch-id") @Valid UUID branchId);

    @ApiOperation(value = "search list branch")
    @GetMapping(value = "/search")
    ServiceResponse<PageResponse<Branch>> searchBranch(@Valid @ValidatePageCriteria(allowSorts = {"createdAt", "updatedAt", "name"})
                                                                                               PageCriteria pageCriteria);

    @ApiOperation(value = "search the nearest branch")
    @GetMapping(value = "/search-nearest/{latitude}-{longitude}-{distance}")
    ServiceResponse<List<Branch>> searchNearestBranch(@PathVariable("latitude") @Valid double latitude,
                                                      @PathVariable("longitude") @Valid double longitude,
                                                      @PathVariable("distance") @Valid double distance);
}
