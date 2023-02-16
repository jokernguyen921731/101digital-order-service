package com.digital.assessment.order.domain;

import com.digital.assessment.order.enums.StatusQueue;
import lombok.*;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Queue {
    private UUID queueId;
    private UUID branchId;
    private String queueCode;
    private int size;
    private int count;
    private int maxCount;
    private int levelCount;
    private StatusQueue status;
}
