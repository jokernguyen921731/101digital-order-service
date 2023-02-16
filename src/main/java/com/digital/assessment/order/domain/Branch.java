package com.digital.assessment.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Branch {
    private UUID branchId;
    private String locationName;
    private String phoneContact;
    private String emailContact;
    @JsonIgnore
    private LocalDateTime openTime;
    @JsonIgnore
    private LocalDateTime closeTime;
    private long openTimeInLong;
    private long closeTimeInLong;
}
