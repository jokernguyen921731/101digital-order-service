package com.digital.assessment.order.repository;

import com.digital.assessment.order.entity.QueueEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface QueueRepository extends JpaRepository<QueueEntity, UUID> {
    @Query("SELECT a FROM QueueEntity a where a.branchId=:branchId AND a.queueCode=:queueCode")
    Optional<QueueEntity> findByQueueCode(UUID branchId, String queueCode);

    @Query("SELECT a FROM QueueEntity a where a.branchId=:branchId AND a.queueId=:queueId")
    Optional<QueueEntity> findByQueueId(UUID branchId, UUID queueId);

    @Query("SELECT a FROM QueueEntity a where a.branchId=:branchId")
    List<QueueEntity> findAllByBranch(UUID branchId);
}
