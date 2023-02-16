package com.digital.assessment.order.web.rest;

import com.digital.assessment.order.domain.Queue;
import com.digital.assessment.order.util.Const;
import com.digital.assessment.order.web.rest.request.queue.QueueCreateRequest;
import com.digital.assessment.order.web.rest.request.queue.QueueUpdateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Api(tags = "Queue REST Resource")
@RequestMapping("/queue")
@Validated
public interface QueueResource {
    @ApiOperation(value = "Create a queue")
    @PostMapping("/create")
    ServiceResponse<Queue> create(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                  @RequestBody @Valid QueueCreateRequest request);

    @ApiOperation(value = "update a queue")
    @PutMapping("/update/{queue-id}")
    ServiceResponse<Queue> update(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                  @PathVariable("queue-id") @Valid UUID queueId,
                                  @RequestBody @Valid QueueUpdateRequest request);

    @ApiOperation(value = "search list queue")
    @GetMapping(value = "/search")
    ServiceResponse<List<Queue>> search(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId);

    @ApiOperation(value = "get detail a queue")
    @GetMapping("/update/{queue-id}")
    ServiceResponse<Queue> getDetail(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                     @PathVariable("queue-id") @Valid UUID queueId);
}
