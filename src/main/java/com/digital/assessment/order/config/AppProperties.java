package com.digital.assessment.order.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "app")
@Data
public class AppProperties {
    private Auth auth = new Auth();

    @Data
    public static final class Auth {
        private RootUser rootUser;
    }

    @Data
    public static final class RootUser {
        private String fullname;
        private String username;
        private String password;
        private String email;
        private String phoneNumber;
    }
}
