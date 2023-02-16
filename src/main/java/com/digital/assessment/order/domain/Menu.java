package com.digital.assessment.order.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Menu {
    private UUID menuId;
    private String name;
    private int price;
    private String moneyType;
    @JsonIgnore
    private List<Branch> branches;
}
