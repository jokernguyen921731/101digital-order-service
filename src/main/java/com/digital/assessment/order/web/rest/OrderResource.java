package com.digital.assessment.order.web.rest;

import com.digital.assessment.order.domain.Order;
import com.digital.assessment.order.util.Const;
import com.digital.assessment.order.web.rest.request.order.OrderCreateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@Api(tags = "Order REST Resource")
@RequestMapping("/order")
@Validated
public interface OrderResource {
    @ApiOperation(value = "create an order")
    @PostMapping("/create")
    ServiceResponse<Order> create(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                  @RequestBody @Valid OrderCreateRequest request);

    @ApiOperation(value = "get detail an order")
    @GetMapping("/get-detail/{order-id}")
    ServiceResponse<Order> getDetail(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                     @PathVariable("order-id") @Valid UUID orderId);

    @ApiOperation(value = "take an order out of queue")
    @PutMapping("/take/{order-id}")
    ServiceResponse<Void> take(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                               @PathVariable("order-id") @Valid UUID orderId);

    @ApiOperation(value = "delete an order")
    @DeleteMapping("/cancel/{order-id}")
    ServiceResponse<Void> cancel(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                 @PathVariable("order-id") @Valid UUID orderId);

    @ApiOperation(value = "search list order in a queue")
    @GetMapping("/search/{queue-id}")
    ServiceResponse<List<Order>> search(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                        @PathVariable("queue-id") @Valid UUID queueId);
}
