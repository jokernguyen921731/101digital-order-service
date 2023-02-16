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
public class BranchUpdateRequest {
    private Double latitude;
    private Double longitude;
    private String locationName;
    private String phoneContact;
    private String emailContact;
    private LocalDateTime openTime;
    private LocalDateTime closeTime;
}
