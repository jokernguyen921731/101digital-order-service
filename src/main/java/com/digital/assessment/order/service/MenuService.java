package com.digital.assessment.order.service;

import com.digital.assessment.order.domain.Menu;
import com.digital.assessment.order.web.rest.request.menu.MenuCreateRequest;
import com.digital.assessment.order.web.rest.request.menu.MenuUpdateRequest;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;

import java.util.UUID;

public interface MenuService {
    Menu createNewItemInMenu(MenuCreateRequest request);
    PageResponse<Menu> getAllMenu(PageCriteria pageCriteria);
    Menu getDetail(UUID menuId);
    Menu updateAnItemInMenu(UUID menuId, MenuUpdateRequest request);
    Void deleteOutOfMenu(UUID menuId);

    Menu createNewItemInLocalBranch(UUID branchId, MenuCreateRequest request);
    Void deleteOutOfBranch(UUID branchId, UUID menuId);
    PageResponse<Menu> getMenuLocalBranch(UUID branchId, PageCriteria pageCriteria);
}
