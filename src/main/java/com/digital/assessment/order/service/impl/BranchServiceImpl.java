package com.digital.assessment.order.service.impl;

import com.digital.assessment.order.domain.Branch;
import com.digital.assessment.order.entity.BranchEntity;
import com.digital.assessment.order.exception.http.InvalidInputError;
import com.digital.assessment.order.exception.http.NotFoundError;
import com.digital.assessment.order.repository.BranchRepository;
import com.digital.assessment.order.service.BranchService;
import com.digital.assessment.order.service.mapper.BranchMapper;
import com.digital.assessment.order.util.ValidatorUtils;
import com.digital.assessment.order.web.rest.request.branch.BranchCreateRequest;
import com.digital.assessment.order.web.rest.request.branch.BranchUpdateRequest;
import com.vsm.vin.common.misc.exception.http.ResponseException;
import com.vsm.vin.common.model.PageCriteria;
import com.vsm.vin.common.model.PageResponse;
import com.vsm.vin.common.persistence.entity.PageCriteriaPageableMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.*;

@Service
@Slf4j
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepository;
    private final BranchMapper branchMapper;
    private final PageCriteriaPageableMapper pageCriteriaPageableMapper;

    public BranchServiceImpl(BranchRepository branchRepository, BranchMapper branchMapper, PageCriteriaPageableMapper pageCriteriaPageableMapper) {
        this.branchRepository = branchRepository;
        this.branchMapper = branchMapper;
        this.pageCriteriaPageableMapper = pageCriteriaPageableMapper;
    }

    @Override
    @Transactional
    public Branch create(BranchCreateRequest request) {
        Optional<BranchEntity> existBranch = branchRepository.findByLatLong(request.getLatitude(), request.getLongitude());
        if (existBranch.isPresent()) {
            throw new ResponseException(InvalidInputError.BRANCH_EXIST.getMessage(), InvalidInputError.BRANCH_EXIST);
        }
        if (!ValidatorUtils.validateEmail(request.getEmailContact())) {
            throw new ResponseException(InvalidInputError.INVALID_EMAIL.getMessage(), InvalidInputError.INVALID_EMAIL);
        }
        var branchEntity = BranchEntity.builder().branchId(UUID.randomUUID())
                .latitude(request.getLatitude())
                .longitude(request.getLongitude())
                .locationName(request.getLocationName())
                .emailContact(request.getEmailContact())
                .phoneContact(request.getPhoneContact())
                .openTime(request.getOpenTime())
                .closeTime(request.getCloseTime())
                .build();
        branchRepository.save(branchEntity);
        return branchMapper.toTarget(branchEntity);
    }

    @Override
    @Transactional
    public Branch update(BranchUpdateRequest request, UUID branchId) {
        var branchEntity = this.findByBranchId(branchId);
        if (Objects.nonNull(request.getLatitude())) {
            branchEntity.setLatitude(request.getLatitude());
        }
        if (Objects.nonNull(request.getLongitude())) {
            branchEntity.setLongitude(request.getLongitude());
        }
        if (Objects.nonNull(request.getLocationName())) {
            branchEntity.setLocationName(request.getLocationName());
        }
        if (Objects.nonNull(request.getEmailContact())) {
            if (!ValidatorUtils.validateEmail(request.getEmailContact())) {
                throw new ResponseException(InvalidInputError.INVALID_EMAIL.getMessage(), InvalidInputError.INVALID_EMAIL);
            }
            branchEntity.setEmailContact(request.getEmailContact());
        }
        if (Objects.nonNull(request.getPhoneContact())) {
            branchEntity.setPhoneContact(request.getPhoneContact());
        }
        if (Objects.nonNull(request.getOpenTime())) {
            branchEntity.setOpenTime(request.getOpenTime());
        }
        if (Objects.nonNull(request.getCloseTime())) {
            branchEntity.setCloseTime(request.getCloseTime());
        }

        branchRepository.save(branchEntity);
        return branchMapper.toTarget(branchEntity);
    }

    @Override
    @Transactional
    public Void delete(UUID branchId) {
        var branchEntity = this.findByBranchId(branchId);
        branchEntity.setDeleted(true);
        branchRepository.save(branchEntity);
        return null;
    }

    @Override
    @Transactional
    public PageResponse<Branch> searchBranch(PageCriteria pageCriteria) {
        if (CollectionUtils.isEmpty(pageCriteria.getSort())) {
            pageCriteria.setSort(Collections.singletonList((
                    "-updatedAt"
            )));
        }
        var pageBranch = branchRepository.findAllWithPaging(pageCriteriaPageableMapper
                .toPageable(pageCriteria));
        var branchEntities = branchMapper.toTarget(pageBranch.getContent());
        return PageResponse.<Branch>builder()
                .count(pageBranch.getTotalElements())
                .rows(branchEntities)
                .page(pageCriteria.getPage())
                .limit(pageCriteria.getLimit())
                .build();
    }

    @Override
    @Transactional
    public List<Branch> searchNearestBranch(double latitude, double longitude, double distance) {
        var branchEntities = branchRepository.findStoresWithInDistance(latitude, longitude, distance);
        return branchMapper.toTarget(branchEntities);
    }

    public BranchEntity findByBranchId(UUID branchId) {
        Optional<BranchEntity> existBranch = branchRepository.findByBranchId(branchId);
        if (existBranch.isEmpty()) {
            throw new ResponseException(NotFoundError.BRANCH_NOT_FOUND.getMessage(), NotFoundError.BRANCH_NOT_FOUND);
        }
        return existBranch.get();
    }
}
