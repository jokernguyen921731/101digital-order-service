package com.digital.assessment.order.repository;

import com.digital.assessment.order.entity.BranchEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface BranchRepository extends JpaRepository<BranchEntity, UUID> {
    String HAVERSINE_FORMULA = "(6371 * acos(cos(radians(:latitude)) * cos(radians(a.latitude)) *" +
            " cos(radians(a.longitude) - radians(:longitude)) + sin(radians(:latitude)) * sin(radians(a.latitude))))";

    @Query("SELECT a FROM BranchEntity a where a.deleted=false AND a.latitude=:latitude and a.longitude=:longitude")
    Optional<BranchEntity> findByLatLong(double latitude, double longitude);

    @Query("SELECT a FROM BranchEntity a where a.deleted=false AND a.branchId=:branchId")
    Optional<BranchEntity> findByBranchId(UUID branchId);

    @Query("SELECT a FROM BranchEntity a WHERE " + HAVERSINE_FORMULA + " < :distanceWithInKM ORDER BY "+ HAVERSINE_FORMULA + " DESC")
    List<BranchEntity> findStoresWithInDistance(double latitude, double longitude, double distanceWithInKM);

    @Query("SELECT a FROM BranchEntity a where a.deleted=false")
    Page<BranchEntity> findAllWithPaging(Pageable pageable);

    @Query("SELECT a FROM BranchEntity a where a.deleted=false")
    List<BranchEntity> findAllBranch();

    @Query("SELECT a FROM BranchEntity a where a.deleted=false AND a.branchId in (:branchIds)")
    List<BranchEntity> findByBranchIds(Set<UUID> branchIds);
}
