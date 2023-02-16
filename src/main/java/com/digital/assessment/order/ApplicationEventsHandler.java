package com.digital.assessment.order;

import com.digital.assessment.order.config.AppProperties;
import com.digital.assessment.order.entity.UserEntity;
import com.digital.assessment.order.enums.Role;
import com.digital.assessment.order.repository.UserRepository;
import com.digital.assessment.order.service.UserAccountService;
import com.digital.assessment.order.web.rest.request.user.UserCreateRequest;
import com.digital.assessment.order.enums.IdentityType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Component
@Slf4j
public class ApplicationEventsHandler {
    private final AppProperties appProperties;
    private final UserRepository userRepository;
    private final UserAccountService userService;

    public ApplicationEventsHandler(AppProperties appProperties,
                                    UserRepository userRepository,
                                    UserAccountService userService) {
        this.appProperties = appProperties;
        this.userRepository = userRepository;
        this.userService = userService;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void onStartup() {
        log.info("===== Application startup =====");
        createRootUser();
        log.info("===== Finish application startup =====");
    }

    @Transactional
    public void createRootUser() {
        AppProperties.RootUser rootUser = appProperties.getAuth().getRootUser();

        Optional<UserEntity> userEntity = userRepository.findByUsernameAndIdentityProviderType(rootUser.getUsername(), IdentityType.LOCAL);
        if (userEntity.isEmpty()) {
            userService.create(UserCreateRequest.builder().username(rootUser.getUsername())
                    .password(rootUser.getPassword())
                    .fullName(rootUser.getFullname())
                    .email(rootUser.getEmail())
                    .phoneNumber(rootUser.getPhoneNumber())
                    .identityType(IdentityType.LOCAL)
                    .role(Role.ADMIN)
                    .build());
            log.info("Initialized root user: " + rootUser.getUsername());
        } else {
            log.info("Root user existed: " + rootUser.getUsername());
        }
    }
}
