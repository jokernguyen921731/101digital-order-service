package com.digital.assessment.order.repository;

import com.digital.assessment.order.entity.MenuEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface MenuRepository extends JpaRepository<MenuEntity, UUID> {
    @Query("SELECT a FROM MenuEntity a where a.deleted=false AND a.name=:name")
    Optional<MenuEntity> findByName(String name);

    @Query("SELECT a FROM MenuEntity a where a.deleted=false AND a.menuId=:menuId")
    Optional<MenuEntity> findByMenuId(UUID menuId);

    @Query("SELECT a FROM MenuEntity a where a.deleted=false AND a.menuId in (:menuIds)")
    List<MenuEntity> findByMenuIds(Set<UUID> menuIds);


    @Query("SELECT a FROM MenuEntity a LEFT JOIN a.branchEntities b where a.deleted=false " +
            "AND (COALESCE(:branchId) is null or b.branchId = :branchId) " +
            "AND a.menuId in (:menuIds)")
    List<MenuEntity> findByMenuIds(UUID branchId, Set<UUID> menuIds);

    @Query("SELECT a FROM MenuEntity a where a.deleted=false")
    List<MenuEntity> findAll();

    @Query("SELECT a FROM MenuEntity a LEFT JOIN a.branchEntities b where a.deleted=false " +
            "AND (COALESCE(:branchId) is null or b.branchId = :branchId)")
    Page<MenuEntity> findAllWithPaging(Pageable pageable, UUID branchId);
}
