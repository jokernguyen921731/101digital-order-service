package com.digital.assessment.order.entity;

import com.digital.assessment.order.domain.Menu;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "branch")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class BranchEntity extends AuditEntity {
    @Id
    @Column(name = "branch_id", updatable = false)
    private UUID branchId;

    @Column(name = "latitude")
    private double latitude;

    @Column(name = "longitude")
    private double longitude;

    @Column(name = "location_name")
    private String locationName;

    @Column(name = "phone_contact")
    private String phoneContact;

    @Column(name = "email_contact")
    private String emailContact;

    @Column(name = "open_time")
    private LocalDateTime openTime;

    @Column(name = "close_time")
    private LocalDateTime closeTime;

    @ManyToMany(mappedBy = "branchEntities", fetch = FetchType.EAGER)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<MenuEntity> menuEntities;

    @Builder.Default
    private Boolean deleted = false;
}
