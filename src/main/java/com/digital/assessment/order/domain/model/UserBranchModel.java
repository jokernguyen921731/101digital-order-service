package com.digital.assessment.order.domain.model;

import com.digital.assessment.order.domain.Branch;
import com.digital.assessment.order.domain.Menu;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserBranchModel {
    private Branch branch;
    private int servedTime;
}
