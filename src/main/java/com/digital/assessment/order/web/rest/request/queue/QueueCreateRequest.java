package com.digital.assessment.order.web.rest.request.queue;

import com.digital.assessment.order.util.Const;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Valid
public class QueueCreateRequest {
    @NotNull(message = "REQUIRE_SIZE")
    @Min(value = 1, message = "REQUIRE_SIZE_GREATER_THAN_ZERO")
    private Integer size;
    @NotEmpty(message = "REQUIRE_QUEUE_CODE")
    private String queueCode;
    @Min(value = 1, message = "REQUIRE_MAX_COUNT_GREATER_THAN_ZERO")
    private Integer maxCount = Const.DEFAULT_MAX_COUNT;
}
