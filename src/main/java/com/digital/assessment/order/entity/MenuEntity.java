package com.digital.assessment.order.entity;

import com.digital.assessment.order.enums.MenuGroup;
import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "menu")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class MenuEntity extends AuditEntity{
    @Id
    @Column(name = "menu_id", updatable = false)
    private UUID menuId;

    @Column(name = "name")
    private String name;

    @Column(name = "price")
    private int price;

    @Column(name = "money_type")
    private String moneyType;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinTable(name = "branch_menu",
            joinColumns = @JoinColumn(name = "menu_id"),
            inverseJoinColumns = @JoinColumn(name = "branch_id")
    )
    private List<BranchEntity> branchEntities;

    @ManyToMany(mappedBy = "menuEntities", fetch = FetchType.LAZY)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<OrderEntity> orderEntities;

    @Column(name = "menu_group")
    @Enumerated(EnumType.STRING)
    private MenuGroup menuGroup;

    @Builder.Default
    private Boolean deleted = false;
}
