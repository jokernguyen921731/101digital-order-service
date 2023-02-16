package com.digital.assessment.order.web.rest.request.order;

import com.digital.assessment.order.enums.StatusPayment;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Valid
public class OrderCreateRequest {
    @NotNull(message = "REQUIRE_MENU_ID")
    private Set<UUID> menuId;
    @NotNull(message = "REQUIRE_USER_ID")
    private UUID userId;
    @NotNull(message = "REQUIRE_QUEUE_ID")
    private UUID queueId;
    @NotBlank(message = "REQUIRE_NOTE")
    private String note;
    @NotNull(message = "REQUIRE_STATUS_PAYMENT")
    private StatusPayment statusPayment;
    @NotNull(message = "REQUIRE_TOTAL_COST")
    @Min(value = 1, message = "REQUIRE_TOTAL_COST_GREATER_THAN_ZERO")
    private int totalCost;
    @NotBlank(message = "REQUIRE_MONEY_TYPE")
    private String moneyType;
}
