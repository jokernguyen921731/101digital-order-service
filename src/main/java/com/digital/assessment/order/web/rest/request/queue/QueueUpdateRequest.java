package com.digital.assessment.order.web.rest.request.queue;

import com.digital.assessment.order.enums.StatusQueue;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Valid
public class QueueUpdateRequest {
    @Min(value = 1, message = "REQUIRE_SIZE_GREATER_THAN_ZERO")
    private Integer size;
    private StatusQueue status;
    @Min(value = 1, message = "REQUIRE_MAX_COUNT_GREATER_THAN_ZERO")
    private Integer maxCount;
}
