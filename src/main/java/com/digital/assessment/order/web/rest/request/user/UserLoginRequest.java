package com.digital.assessment.order.web.rest.request.user;

import com.digital.assessment.order.enums.IdentityType;
import com.digital.assessment.order.util.Const;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
@Valid
public class UserLoginRequest {
    @NotBlank(message = "USERNAME_REQUIRED")
    private String username;
    @NotBlank(message = "PASSWORD_REQUIRED")
    private String password;

//    private IdentityType identityType;
}
