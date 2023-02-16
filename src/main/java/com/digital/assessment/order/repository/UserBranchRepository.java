package com.digital.assessment.order.repository;

import com.digital.assessment.order.entity.UserBranchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserBranchRepository extends JpaRepository<UserBranchEntity, UUID> {
    @Query("SELECT a FROM UserBranchEntity a where a.branchId=:branchId AND a.userId=:userId")
    Optional<UserBranchEntity> findByBranchId(UUID branchId, UUID userId);

    @Query("SELECT a FROM UserBranchEntity a where a.userId=:userId")
    List<UserBranchEntity> findByUserId(UUID userId);
}
