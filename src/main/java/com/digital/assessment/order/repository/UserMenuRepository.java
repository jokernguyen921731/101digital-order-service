package com.digital.assessment.order.repository;

import com.digital.assessment.order.entity.UserMenuEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserMenuRepository extends JpaRepository<UserMenuEntity, UUID> {
    @Query("SELECT a FROM UserMenuEntity a where a.userId=:userId AND a.menuId in (:menuIds)")
    List<UserMenuEntity> findByMenuIds(UUID userId, Set<UUID> menuIds);

    @Query("SELECT a FROM UserMenuEntity a where a.userId=:userId")
    List<UserMenuEntity> findByUserId(UUID userId);
}
