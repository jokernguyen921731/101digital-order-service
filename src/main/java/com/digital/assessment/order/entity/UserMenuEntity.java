package com.digital.assessment.order.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Table(name = "user_menu")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserMenuEntity extends AuditEntity{
    @Id
    @Column(name = "id", updatable = false)
    private UUID id;

    @Column(name = "menu_id")
    private UUID menuId;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "item_count")
    private int itemCount;
}
