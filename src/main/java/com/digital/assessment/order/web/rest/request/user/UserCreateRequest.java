package com.digital.assessment.order.web.rest.request.user;

import com.digital.assessment.order.enums.IdentityType;
import com.digital.assessment.order.enums.Role;
import com.digital.assessment.order.util.Const;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vsm.vin.common.model.iam.enums.ObjectStatus;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.Email;
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
public class UserCreateRequest {
    @Size(max = Const.USERNAME.USERNAME_MAX_LENGTH, min = Const.USERNAME.USERNAME_MIN_LENGTH, message = "LENGTH_USERNAME")
    @NotBlank(message = "USERNAME_REQUIRED")
    private String username;

    @JsonIgnore
    @Size(max = Const.PASSWORD.PW_MAX_LENGTH, min = Const.PASSWORD.PW_MIN_LENGTH, message = "LENGTH_PASSWORD")
    @Pattern(regexp = Const.PASSWORD.PW_REGEX,
            message = "FORMAT_PASSWORD")
    private String password;

    @Size(max = Const.FULL_NAME.NAME_MAX_LENGTH, min = Const.FULL_NAME.NAME_MIN_LENGTH, message = "LENGTH_FULL_NAME")
    @NotBlank(message = "FULL_NAME_REQUIRED")
    private String fullName;

    @NotBlank(message = "EMAIL_REQUIRED")
    @Size(max = Const.EMAIL.EMAIL_MAX_LENGTH, message = "LENGTH_EMAIL")
    @Email(regexp = Const.EMAIL.EMAIL_REGEX,
            flags = Pattern.Flag.CASE_INSENSITIVE,
            message = "EMAIL_WRONG_FORMAT")
    private String email;

    @NotBlank(message = "MOBILE_REQUIRED")
    @Pattern(regexp = Const.PHONE_REGEX, message = "FORMAT_PHONE_NUMBER")
    private String phoneNumber;

    @JsonIgnore
    private ObjectStatus userStatus = ObjectStatus.ACTIVE;

    @JsonIgnore
    private IdentityType identityType;

    @JsonIgnore
    private Role role;
}
