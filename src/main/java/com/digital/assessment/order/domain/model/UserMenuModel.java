package com.digital.assessment.order.domain.model;

import com.digital.assessment.order.domain.Menu;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class UserMenuModel {
    private Menu menu;
    private int itemCount;
}
