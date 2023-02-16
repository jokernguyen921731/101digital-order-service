package com.digital.assessment.order.domain;

import com.digital.assessment.order.domain.model.Token;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginData {
    private Token token;
    private User user;
}
