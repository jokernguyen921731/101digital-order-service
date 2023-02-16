package com.digital.assessment.order.service.impl;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.provider.ClientDetails;

import java.util.*;

public class ClientDetailsImpl implements ClientDetails {

    private String clientId;
    private String clientSecret;

    public ClientDetailsImpl(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String getClientId() {
        return this.clientId;
    }

    @Override
    public Set<String> getResourceIds() {
        HashSet<String> resourceId = new HashSet<>();
        resourceId.add("oauth2-resource");
        return resourceId;
    }

    @Override
    public boolean isSecretRequired() {
        return true;
    }

    @Override
    public String getClientSecret() {
        return this.clientSecret;
    }

    @Override
    public boolean isScoped() {
        return true;
    }

    @Override
    public Set<String> getScope() {
        HashSet<String> scope = new HashSet<>();
        scope.add("read");
        scope.add("write");
        return scope;
    }

    @Override
    public Set<String> getAuthorizedGrantTypes() {
        HashSet<String> grantTypes = new HashSet<>();
        grantTypes.add("password");
        grantTypes.add("refresh_token");
        grantTypes.add("check_token");
        grantTypes.add("authorization_code");
        grantTypes.add("client_credentials");
        return grantTypes;
    }

    @Override
    public Set<String> getRegisteredRedirectUri() {
        return null;
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        GrantedAuthority g2 = new SimpleGrantedAuthority("READ");
        List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        grantedAuthorities.add(g2);
        return grantedAuthorities;
    }

    @Override
    public Integer getAccessTokenValiditySeconds() {
        return 9999;
    }

    @Override
    public Integer getRefreshTokenValiditySeconds() {
        return 9999;
    }

    @Override
    public boolean isAutoApprove(String s) {
        return true;
    }

    @Override
    public Map<String, Object> getAdditionalInformation() {
        return null;
    }
}
