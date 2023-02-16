package com.digital.assessment.order.service.impl;

import com.digital.assessment.order.domain.LoginData;
import com.digital.assessment.order.domain.User;
import com.digital.assessment.order.domain.model.Token;
import com.digital.assessment.order.domain.model.UserBranchModel;
import com.digital.assessment.order.domain.model.UserMenuModel;
import com.digital.assessment.order.entity.*;
import com.digital.assessment.order.enums.IdentityType;
import com.digital.assessment.order.enums.UserStatus;
import com.digital.assessment.order.exception.http.AuthorizedError;
import com.digital.assessment.order.exception.http.ForbiddenError;
import com.digital.assessment.order.repository.*;
import com.digital.assessment.order.service.UserAccountService;
import com.digital.assessment.order.service.mapper.BranchMapper;
import com.digital.assessment.order.service.mapper.MenuMapper;
import com.digital.assessment.order.service.mapper.UserMapper;
import com.digital.assessment.order.util.Const;
import com.digital.assessment.order.web.rest.request.user.UserCreateRequest;
import com.digital.assessment.order.web.rest.request.user.UserLoginRequest;
import com.vsm.vin.common.misc.exception.http.ResponseException;
import com.vsm.vin.common.web.exception.UnauthorizedError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.security.oauth2.provider.endpoint.TokenEndpoint;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class UserAccountServiceImpl implements UserAccountService {

    private final UserRepository userRepository;

    private PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    private TokenEndpoint tokenEndpoint = new TokenEndpoint();
    private final UserMapper userMapper;
    private final UserBranchRepository userBranchRepository;
    private final UserMenuRepository userMenuRepository;
    private final BranchRepository branchRepository;
    private final MenuRepository menuRepository;
    private final BranchMapper branchMapper;
    private final MenuMapper menuMapper;
    private String clientId = Const.CLIENT.CLIENT_ID;
    private String clientSecret = Const.CLIENT.CLIENT_SECRET;

    public UserAccountServiceImpl(UserRepository userRepository,
                                  UserMapper userMapper,
                                  UserBranchRepository userBranchRepository,
                                  UserMenuRepository userMenuRepository,
                                  BranchRepository branchRepository,
                                  MenuRepository menuRepository,
                                  BranchMapper branchMapper,
                                  MenuMapper menuMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.userBranchRepository = userBranchRepository;
        this.userMenuRepository = userMenuRepository;
        this.branchRepository = branchRepository;
        this.menuRepository = menuRepository;
        this.branchMapper = branchMapper;
        this.menuMapper = menuMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Void create(UserCreateRequest request) {
        userRepository.save(UserEntity.builder().userId(UUID.randomUUID())
                .username(request.getUsername())
                .password(this.passwordEncoder.encode(request.getPassword()))
                .fullName(request.getFullName())
                .phoneNumber(request.getPhoneNumber())
                .email(request.getEmail())
                .role(request.getRole())
                .identityType(request.getIdentityType())
                .userStatus(UserStatus.ACTIVE)
                .build());
        return null;
    }

    @Override
    public LoginData login(HttpServletRequest httpServletRequest, UserLoginRequest request) throws Exception {
        var username = request.getUsername().trim().toLowerCase();
        Optional<UserEntity> existUser = userRepository.findByUsernameAndIdentityProviderType(username, IdentityType.LOCAL);
        if (existUser.isEmpty()) {
            throw new ResponseException(UnauthorizedError.LOGIN_FAIL.getMessage(), UnauthorizedError.LOGIN_FAIL);
        }

        var userEntity = existUser.get();
        if (UserStatus.INACTIVE.equals(userEntity.getUserStatus())) {
            throw new ResponseException(ForbiddenError.ACCOUNT_NOT_ACTIVATED.getMessage(),
                    ForbiddenError.ACCOUNT_NOT_ACTIVATED);
        }
        if (!this.passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new ResponseException(AuthorizedError.NON_AUTHORIZED_INFORMATION.getMessage(),
                    AuthorizedError.NON_AUTHORIZED_INFORMATION);
        }

        var authToken = this.generateToken(httpServletRequest,
                makeRequestAuthenticationParameterLogin(userEntity, request.getPassword()));
        var user = userMapper.toTarget(userEntity);
        enrichUser(userEntity, user);
        return LoginData.builder().token(authToken).user(user).build();
    }

    private void enrichUser(UserEntity userEntity, User user) {
        // enrich servedTime with branch information
        List<UserBranchModel> userBranchModels = new ArrayList<>();
        var userBranchEntities = userBranchRepository.findByUserId(userEntity.getUserId());
        var branchIds = userBranchEntities.stream().map(UserBranchEntity::getBranchId).collect(Collectors.toSet());
        var branchEntities = branchRepository.findByBranchIds(branchIds);
        var branchMapEntities = branchEntities.stream().collect(Collectors.toMap(BranchEntity::getBranchId, o->o));
        userBranchEntities.forEach(userBranch -> {
            var branchId = userBranch.getBranchId();
            var servedTime = userBranch.getServedTime();
            var branchEntity = branchMapEntities.get(branchId);
            if (Objects.nonNull(branchEntity)) {
                userBranchModels.add(UserBranchModel.builder()
                        .branch(branchMapper.toTarget(branchEntity))
                        .servedTime(servedTime)
                        .build());
            }
        });

        // enrich itemCount with menu information
        List<UserMenuModel> userMenuModels = new ArrayList<>();
        var userMenuEntities = userMenuRepository.findByUserId(userEntity.getUserId());
        var menuIds = userMenuEntities.stream().map(UserMenuEntity::getMenuId).collect(Collectors.toSet());
        var menuEntities = menuRepository.findByMenuIds(menuIds);
        var menuMapEntities = menuEntities.stream().collect(Collectors.toMap(MenuEntity::getMenuId, o->o));
        userMenuEntities.forEach(userMenu -> {
            var menuId = userMenu.getMenuId();
            var itemCount = userMenu.getItemCount();
            var menuEntity = menuMapEntities.get(menuId);
            if (Objects.nonNull(menuEntity)) {
                userMenuModels.add(UserMenuModel.builder()
                        .menu(menuMapper.toTarget(menuEntity))
                        .itemCount(itemCount)
                        .build());
            }
        });

        user.setUserBranchModels(userBranchModels);
        user.setUserMenuModels(userMenuModels);
    }

    private Token generateToken(HttpServletRequest request, Map<String, String> requestParameters) {
        try {
            if (StringUtils.hasLength(request.getHeader("Client-Id"))) {
                clientId = request.getHeader("Client-Id");
                clientSecret = request.getHeader("Client-Secret");
            }
            Set<GrantedAuthority> authorities = new HashSet<>();
            UsernamePasswordAuthenticationToken authenticationToken = new
                    UsernamePasswordAuthenticationToken(clientId, clientSecret, authorities);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            ResponseEntity<OAuth2AccessToken> responseEntity = tokenEndpoint.postAccessToken(request.getUserPrincipal(), requestParameters);
            var oAuth2AccessToken = responseEntity.getBody();
            if (Objects.nonNull(oAuth2AccessToken)) {
                return Token.builder()
                        .accessToken(oAuth2AccessToken.getValue())
                        .tokenType(oAuth2AccessToken.getTokenType())
                        .expiresIn(oAuth2AccessToken.getExpiresIn())
                        .refreshToken(oAuth2AccessToken.getRefreshToken().getValue())
                        .build();
            }
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
        return null;
    }

    private Map<String, String> makeRequestAuthenticationParameterLogin(UserEntity userEntity, String password) {
        Map<String, String> requestParameters = new HashMap<>();
        requestParameters.put("grant_type", "password");
        requestParameters.put("username", userEntity.getUserId().toString());
        requestParameters.put("password", password);
        return requestParameters;
    }
}
