package com.digital.assessment.order.service.impl;

import com.digital.assessment.order.util.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
@Primary
public class ClientDetailsServiceImpl implements ClientDetailsService {
    @Value("${security.oauth2.authorization.jwt.expires-in:1h}")
    private Duration expiresIn;

    @Value("${security.oauth2.authorization.jwt.refresh-token-expires-in:30d}")
    private Duration refreshTokenExpiresIn;

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Autowired
    public ClientDetailsServiceImpl() {}

    @Override
    public ClientDetails loadClientByClientId(String s) throws ClientRegistrationException {
        var clientSecret = this.passwordEncoder.encode(Const.CLIENT.CLIENT_SECRET);
        ClientDetailsImpl clientDetails = new ClientDetailsImpl(Const.CLIENT.CLIENT_ID, clientSecret);
        BaseClientDetails baseClientDetails = new BaseClientDetails(clientDetails);
        baseClientDetails.setAccessTokenValiditySeconds((int) expiresIn.toSeconds());
        baseClientDetails.setRefreshTokenValiditySeconds((int) refreshTokenExpiresIn.toSeconds());
        return baseClientDetails;
    }
}
