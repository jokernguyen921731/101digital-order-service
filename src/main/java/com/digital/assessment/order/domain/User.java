package com.digital.assessment.order.domain;

import com.digital.assessment.order.domain.model.UserBranchModel;
import com.digital.assessment.order.domain.model.UserMenuModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class User {
    private UUID userId;
    private String username;
    private String fullName;
    private String phoneNumber;
    private String email;
    private String address;
    private Instant expiredAt;
    private Instant availableAt;

    private List<UserMenuModel> userMenuModels;
    private List<UserBranchModel> userBranchModels;
}
