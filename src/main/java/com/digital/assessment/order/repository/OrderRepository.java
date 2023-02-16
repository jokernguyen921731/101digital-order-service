package com.digital.assessment.order.repository;

import com.digital.assessment.order.entity.OrderEntity;
import com.digital.assessment.order.enums.StatusOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<OrderEntity, UUID> {
    @Query("SELECT a FROM OrderEntity a where a.deleted=false AND a.branchId=:branchId AND a.orderId=:orderId")
    Optional<OrderEntity> findByOrderId(UUID branchId, UUID orderId);

    @Query("SELECT a FROM OrderEntity a where a.deleted=false AND a.branchId=:branchId AND a.queueId=:queueId " +
            "AND (a.status=:newStatus OR a.status=:processStatus)" +
            "ORDER BY a.sequenceLevel DESC, sequenceNumber DESC")
    List<OrderEntity> findNewOrProcessOrderInQueue(UUID branchId, UUID queueId, StatusOrder newStatus, StatusOrder processStatus);
}
