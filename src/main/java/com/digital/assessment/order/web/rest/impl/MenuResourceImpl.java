package com.digital.assessment.order.web.rest.impl;

import com.digital.assessment.order.domain.Menu;
import com.digital.assessment.order.service.MenuService;
import com.digital.assessment.order.web.rest.MenuResource;
import com.digital.assessment.order.web.rest.request.menu.MenuCreateRequest;
import com.digital.assessment.order.web.rest.request.menu.MenuUpdateRequest;
import com.digital.assessment.order.web.rest.response.ServiceResponse;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@Slf4j
public class MenuResourceImpl implements MenuResource {
    private final MenuService menuService;

    public MenuResourceImpl(MenuService menuService) {
        this.menuService = menuService;
    }

    @Override
    public ServiceResponse<Menu> createNewItemInMenu(MenuCreateRequest request) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.createNewItemInMenu(request));
    }

    @Override
    public ServiceResponse<Menu> updateAnItemInMenu(UUID menuId, MenuUpdateRequest request) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.updateAnItemInMenu(menuId, request));
    }

    @Override
    public ServiceResponse<PageResponse<Menu>> search(PageCriteria pageCriteria) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.getAllMenu(pageCriteria));
    }

    @Override
    public ServiceResponse<Menu> getDetail(UUID menuId) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.getDetail(menuId));
    }

    @Override
    public ServiceResponse<Void> deleteOutOfMenu(UUID menuId) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.deleteOutOfMenu(menuId));
    }

    @Override
    public ServiceResponse<Menu> createNewItemInLocalBranch(UUID branchId, MenuCreateRequest request) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.createNewItemInLocalBranch(branchId, request));
    }

    @Override
    public ServiceResponse<Void> deleteOutOfBranch(UUID branchId, UUID menuId) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.deleteOutOfBranch(branchId, menuId));
    }

    @Override
    public ServiceResponse<PageResponse<Menu>> searchLocalBranch(UUID branchId, PageCriteria pageCriteria) {
        return ServiceResponse.succeed(HttpStatus.OK, menuService.getMenuLocalBranch(branchId, pageCriteria));
    }
}
