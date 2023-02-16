package com.digital.assessment.order.web.rest.request.menu;

import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Valid
public class MenuCreateRequest {
    @NotBlank(message = "REQUIRE_NAME")
    private String name;
    @NotNull(message = "REQUIRE_NAME")
    @Min(value = 1, message = "REQUIRE_PRICE_GREATER_THAN_ZERO")
    private int price;
    @NotBlank(message = "REQUIRE_MONEY_TYPE")
    private String moneyType;
}
