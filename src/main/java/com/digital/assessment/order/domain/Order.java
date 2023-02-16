package com.digital.assessment.order.domain;

import com.digital.assessment.order.enums.StatusOrder;
import com.digital.assessment.order.enums.StatusPayment;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Order {
    private UUID orderId;
    private UUID branchId;
    private UUID queueId;
    private UUID customerId;
    private int sequenceLevel;
    private int sequenceNumber;
    private StatusOrder status;
    private String note;
    private StatusPayment statusPayment;
    private int totalCost;
    private String moneyType;

    private String sequence;
    private int indexInQueue;
    private Branch branch;
    private Queue queue;
    private List<Menu> menu;
    private User user;
}
