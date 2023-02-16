package com.digital.assessment.order.entity;

import com.digital.assessment.order.enums.StatusOrder;
import com.digital.assessment.order.enums.StatusPayment;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "order_tbl")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class OrderEntity extends AuditEntity {
    @Id
    @Column(name = "order_id", updatable = false)
    private UUID orderId;

    @Column(name = "branch_id")
    private UUID branchId;

    @Column(name = "queue_id")
    private UUID queueId;

    @Column(name = "customer_id")
    private UUID customerId;

    @Column(name = "sequence_level")
    private int sequenceLevel;

    @Column(name = "sequence_number")
    private int sequenceNumber;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusOrder status;

    @Column(name = "note")
    private String note;

    @Column(name = "status_payment")
    @Enumerated(EnumType.STRING)
    private StatusPayment statusPayment;

    @Column(name = "total_cost")
    private int totalCost;

    @Column(name = "money_type")
    private String moneyType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "order_menu",
            joinColumns = @JoinColumn(name = "order_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id")
    )
    private List<MenuEntity> menuEntities;

    @Builder.Default
    private Boolean deleted = false;
}
