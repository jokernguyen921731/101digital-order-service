package com.digital.assessment.order.entity;

import com.digital.assessment.order.enums.StatusQueue;
import lombok.*;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "queue")
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class QueueEntity extends AuditEntity {
    @Id
    @Column(name = "queue_id", updatable = false)
    private UUID queueId;

    @Column(name = "branch_id")
    private UUID branchId;

    @Column(name = "queue_code")
    private String queueCode;

    @Column(name = "size")
    private int size;

    @Column(name = "count")
    private int count;

    @Column(name = "max_count")
    private int maxCount;

    @Column(name = "level_count")
    private int levelCount;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private StatusQueue status;
}
