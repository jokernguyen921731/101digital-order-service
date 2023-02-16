package com.digital.assessment.order.web.rest.request.branch;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Valid
public class BranchCreateRequest {
    @NotNull(message = "REQUIRE_LATITUDE")
    private double latitude;
    @NotNull(message = "REQUIRE_LONGITUDE")
    private double longitude;
    @NotEmpty(message = "REQUIRE_LOCATION_NAME")
    private String locationName;
    @NotEmpty(message = "REQUIRE_PHONE_CONTRACT")
    private String phoneContact;
    @NotEmpty(message = "REQUIRE_EMAIL_CONTRACT")
    private String emailContact;
    @NotNull(message = "REQUIRE_OPEN_TIME")
    private LocalDateTime openTime;
    @NotNull(message = "REQUIRE_CLOSE_TIME")
    private LocalDateTime closeTime;
}
