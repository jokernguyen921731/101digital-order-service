package com.digital.assessment.order.service.impl;

import com.digital.assessment.order.domain.Menu;
import com.digital.assessment.order.entity.MenuEntity;
import com.digital.assessment.order.enums.MenuGroup;
import com.digital.assessment.order.exception.http.InvalidInputError;
import com.digital.assessment.order.exception.http.NotFoundError;
import com.digital.assessment.order.repository.BranchRepository;
import com.digital.assessment.order.repository.MenuRepository;
import com.digital.assessment.order.service.MenuService;
import com.digital.assessment.order.service.mapper.BranchMapper;
import com.digital.assessment.order.service.mapper.MenuMapper;
import com.digital.assessment.order.web.rest.request.menu.MenuCreateRequest;
import com.digital.assessment.order.web.rest.request.menu.MenuUpdateRequest;
import com.vsm.vin.common.misc.exception.http.ResponseException;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;
import com.vsm.vin.common.persistence.entity.PageCriteriaPageableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.*;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService {
    private final MenuRepository menuRepository;
    private final BranchRepository branchRepository;
    private final MenuMapper menuMapper;
    private final PageCriteriaPageableMapper pageCriteriaPageableMapper;
    private final BranchMapper branchMapper;
    private final BranchServiceImpl branchService;

    public MenuServiceImpl(MenuRepository menuRepository, BranchRepository branchRepository, MenuMapper menuMapper, PageCriteriaPageableMapper pageCriteriaPageableMapper, BranchMapper branchMapper, BranchServiceImpl branchService) {
        this.menuRepository = menuRepository;
        this.branchRepository = branchRepository;
        this.menuMapper = menuMapper;
        this.pageCriteriaPageableMapper = pageCriteriaPageableMapper;
        this.branchMapper = branchMapper;
        this.branchService = branchService;
    }

    @Override
    @Transactional
    public Menu createNewItemInMenu(MenuCreateRequest request) {
        this.checkExistItemByName(request.getName());
        var menuEntity = MenuEntity.builder()
                .menuId(UUID.randomUUID())
                .name(request.getName())
                .moneyType(request.getMoneyType())
                .menuGroup(MenuGroup.COFFEE)
                .price(request.getPrice()).build();
        var branchEntities = branchRepository.findAllBranch();
        if (!CollectionUtils.isEmpty(branchEntities)) {
            menuEntity.setBranchEntities(branchEntities);
        }
        menuRepository.save(menuEntity);
        return menuMapper.toTarget(menuEntity);
    }

    @Override
    @Transactional
    public PageResponse<Menu> getAllMenu(PageCriteria pageCriteria) {
        if (CollectionUtils.isEmpty(pageCriteria.getSort())) {
            pageCriteria.setSort(Collections.singletonList((
                    "-updatedAt"
            )));
        }
        var pageMenu = menuRepository.findAllWithPaging(pageCriteriaPageableMapper.toPageable(pageCriteria), null);
        if (!CollectionUtils.isEmpty(pageMenu.getContent())) {
            var menuEntities = menuMapper.toTarget(pageMenu.getContent());
            enrichMenu(pageMenu.getContent(), menuEntities, true);
            return PageResponse.<Menu>builder()
                    .count(pageMenu.getTotalElements())
                    .rows(menuEntities)
                    .page(pageCriteria.getPage())
                    .limit(pageCriteria.getLimit())
                    .build();
        }
        return new PageResponse<>();
    }

    @Override
    public Menu getDetail(UUID menuId) {
        var menuEntity = this.findItemByMenuId(menuId);
        return menuMapper.toTarget(menuEntity);
    }

    @Override
    @Transactional
    public Menu updateAnItemInMenu(UUID menuId, MenuUpdateRequest request) {
        var menuEntity = this.findItemByMenuId(menuId);
        if (StringUtils.hasLength(request.getName())) {
            menuEntity.setName(request.getName());
        }
        if (StringUtils.hasLength(request.getMoneyType())) {
            menuEntity.setMoneyType(request.getMoneyType());
        }
        if (request.getPrice() > 0) {
            menuEntity.setPrice(request.getPrice());
        }
        menuRepository.save(menuEntity);
        return menuMapper.toTarget(menuEntity);
    }

    @Override
    @Transactional
    public Void deleteOutOfMenu(UUID menuId) {
        var menuEntity = this.findItemByMenuId(menuId);
        menuEntity.setDeleted(true);
        menuRepository.save(menuEntity);
        return null;
    }

    @Override
    @Transactional
    public Menu createNewItemInLocalBranch(UUID branchId, MenuCreateRequest request) {
        var existBranch = branchRepository.findByBranchId(branchId);
        if (existBranch.isEmpty()) {
            throw new ResponseException(NotFoundError.BRANCH_NOT_FOUND.getMessage(), NotFoundError.BRANCH_NOT_FOUND);
        }
        this.checkExistItemByName(request.getName());
        var branchEntity = existBranch.get();
        var menuEntity = MenuEntity.builder().menuId(UUID.randomUUID())
                .name(request.getName())
                .moneyType(request.getMoneyType())
                .price(request.getPrice())
                .branchEntities(List.of(branchEntity)).build();
        menuRepository.save(menuEntity);
        return menuMapper.toTarget(menuEntity);
    }

    @Override
    @Transactional
    public Void deleteOutOfBranch(UUID branchId, UUID menuId) {
        branchService.findByBranchId(branchId);
        var menuEntity = this.findItemByMenuId(menuId);
        var branchInList = menuEntity.getBranchEntities().stream().filter(f->f.getBranchId().equals(branchId)).findFirst();
        if (branchInList.isPresent()) {
            menuEntity.getBranchEntities().remove(branchInList.get());
            menuEntity.setBranchEntities(menuEntity.getBranchEntities());
        }
        menuRepository.save(menuEntity);
        return null;
    }

    @Override
    @Transactional
    public PageResponse<Menu> getMenuLocalBranch(UUID branchId, PageCriteria pageCriteria) {
        branchService.findByBranchId(branchId);
        if (CollectionUtils.isEmpty(pageCriteria.getSort())) {
            pageCriteria.setSort(Collections.singletonList((
                    "-updatedAt"
            )));
        }
        var pageMenu = menuRepository.findAllWithPaging(pageCriteriaPageableMapper.toPageable(pageCriteria), branchId);
        if (!CollectionUtils.isEmpty(pageMenu.getContent())) {
            var menuEntities = menuMapper.toTarget(pageMenu.getContent());
            enrichMenu(pageMenu.getContent(), menuEntities, false);
            return PageResponse.<Menu>builder()
                    .count(pageMenu.getTotalElements())
                    .rows(menuEntities)
                    .page(pageCriteria.getPage())
                    .limit(pageCriteria.getLimit())
                    .build();
        }
        return new PageResponse<>();
    }

    private void enrichMenu(List<MenuEntity> menuEntities, List<Menu> menuMapper, boolean needBranches) {
        if (needBranches) {
            menuMapper.forEach(menu -> {
                var menuEntity = menuEntities.stream()
                        .filter(f->f.getMenuId().equals(menu.getMenuId())).findFirst().get();
                var branchEntities = menuEntity.getBranchEntities();
                if (!CollectionUtils.isEmpty(branchEntities)) {
                    menu.setBranches(branchMapper.toTarget(branchEntities));
                }
            });
        }
    }

    public void checkExistItemByName(String name) {
        var existMenu = menuRepository.findByName(name);
        if (existMenu.isPresent()) {
            throw new ResponseException(InvalidInputError.MENU_EXIST.getMessage(), InvalidInputError.MENU_EXIST);
        }
    }

    public MenuEntity findItemByMenuId(UUID menuId) {
        var existMenu = menuRepository.findByMenuId(menuId);
        if (existMenu.isEmpty()) {
            throw new ResponseException(NotFoundError.MENU_NOT_FOUND.getMessage(), NotFoundError.MENU_NOT_FOUND);
        }
        return existMenu.get();
    }
}
