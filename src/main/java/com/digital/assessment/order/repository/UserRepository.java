package com.digital.assessment.order.repository;

import com.digital.assessment.order.domain.User;
import com.digital.assessment.order.entity.UserEntity;
import com.digital.assessment.order.enums.IdentityType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<UserEntity, User> {
    @Query("SELECT a FROM UserEntity a where a.userId=:userId AND a.deleted=false")
    Optional<UserEntity> findByUserId(UUID userId);

    @Query("select u from UserEntity  u " +
            "where lower(u.username) = lower(:username) and u.identityType = :type")
    Optional<UserEntity> findByUsernameAndIdentityProviderType(String username, IdentityType type);
}
