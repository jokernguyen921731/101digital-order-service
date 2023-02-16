package com.digital.assessment.order.web.rest;

import com.digital.assessment.order.domain.Menu;
import com.digital.assessment.order.util.Const;
import com.digital.assessment.order.web.rest.request.menu.MenuCreateRequest;
import com.digital.assessment.order.web.rest.request.menu.MenuUpdateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import com.digital.assessment.order.web.rest.validator.ValidatePageCriteria;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.websocket.server.PathParam;
import java.util.UUID;

@Api(tags = "Menu REST Resource")
@RequestMapping("/menu")
@Validated
public interface MenuResource {
    @ApiOperation(value = "create an item")
    @PostMapping("/create-new-item")
    ServiceResponse<Menu> createNewItemInMenu(@RequestBody @Valid MenuCreateRequest request);

    @ApiOperation(value = "update an item")
    @PutMapping("/update/{menu-id}")
    ServiceResponse<Menu> updateAnItemInMenu(@PathVariable(name = "menu-id") @Valid UUID menuId,
                                 @RequestBody @Valid MenuUpdateRequest request);

    @ApiOperation(value = "search list menu")
    @GetMapping(value = "/search")
    ServiceResponse<PageResponse<Menu>> search(@Valid @ValidatePageCriteria(allowSorts = {"createdAt", "updatedAt", "name"})
                                                               PageCriteria pageCriteria);

    @ApiOperation(value = "get an item")
    @GetMapping(value = "/get-detail/{menu-id}")
    ServiceResponse<Menu> getDetail(@PathVariable(name = "menu-id") @Valid UUID menuId);

    @ApiOperation(value = "delete an item")
    @DeleteMapping("/delete/{menu-id}")
    ServiceResponse<Void> deleteOutOfMenu(@PathVariable(name = "menu-id") @Valid UUID menuId);

    @ApiOperation(value = "create an item in local branch")
    @PostMapping("/create-new-item-local-branch")
    ServiceResponse<Menu> createNewItemInLocalBranch(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                              @RequestBody @Valid MenuCreateRequest request);

    @ApiOperation(value = "delete an item out of branch")
    @DeleteMapping("/delete-local-branch/{menu-id}")
    ServiceResponse<Void> deleteOutOfBranch(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                            @PathVariable(name = "menu-id") @Valid UUID menuId);

    @ApiOperation(value = "search list menu in local branch")
    @GetMapping(value = "/search-local-branch")
    ServiceResponse<PageResponse<Menu>> searchLocalBranch(@RequestHeader(name = Const.BRANCH_ID_HEADER) @NotNull UUID branchId,
                                               @Valid @ValidatePageCriteria(allowSorts = {"createdAt", "updatedAt", "name"})
                                                       PageCriteria pageCriteria);
}
