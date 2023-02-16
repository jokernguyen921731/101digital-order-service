package com.digital.assessment.order.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

import java.util.Optional;

@Data
@Builder
public class Token {
    public static final String TOKEN_TYPE_BEARER = "Bearer";
    private static final String FULL_TOKEN_FORMAT = "%s %s";
    @JsonProperty("access_token")
    private String accessToken;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonProperty("refresh_token")
    private String refreshToken;
    @JsonProperty("id_token")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String idToken;
    @JsonProperty("expires_in")
    private int expiresIn;
    @JsonProperty("token_type")
    private String tokenType;

    @JsonIgnore
    public String getFullToken() {
        return String.format("%s %s", Optional.ofNullable(this.tokenType).orElse(""), Optional.ofNullable(this.accessToken).orElse(""));
    }
}
