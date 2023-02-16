package com.digital.assessment.order.entity;

import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "user_branch")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserBranchEntity{
    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "branch_id")
    private UUID branchId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "served_time")
    private int servedTime;
}
