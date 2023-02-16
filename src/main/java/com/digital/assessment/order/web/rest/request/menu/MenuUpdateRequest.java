package com.digital.assessment.order.web.rest.request.menu;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Valid
public class MenuUpdateRequest {
    private String name;
    @Min(value = 1, message = "REQUIRE_PRICE_GREATER_THAN_ZERO")
    private int price;
    private String moneyType;
}
