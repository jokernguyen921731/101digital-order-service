package com.digital.assessment.order.entity;

import com.digital.assessment.order.enums.IdentityType;
import com.digital.assessment.order.enums.Role;
import com.digital.assessment.order.enums.UserStatus;
import lombok.*;

import javax.persistence.*;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "user_tbl", indexes = {
        @Index(name = "phone_number_idx", columnList = "phone_number"),
        @Index(name = "full_name_idx", columnList = "full_name")
})
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class UserEntity extends AuditEntity{
    @Id
    @Column(name = "user_id", updatable = false)
    private UUID userId;

    @Column(name = "username")
    private String username;

    @Column(length = 100)
    private String password;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "email")
    private String email;

    @Column(name = "address")
    private String address;

    @Builder.Default
    private Boolean deleted = false;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "identity_type")
    @Enumerated(EnumType.STRING)
    private IdentityType identityType;

    @Column(name = "expired_at")
    private Instant expiredAt;

    @Column(name = "available_at")
    private Instant availableAt;

    @Column(name = "user_status")
    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;
}
