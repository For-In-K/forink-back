package com.forink.forink.global.security.util;

import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleClient {

    @Value("${spring.security.oauth2.client.registration.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.registration.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.registration.google.scope}")
    private List<String> scopes;

    @Value("${spring.security.oauth2.client.provider.google.authorization-uri}")
    private String authorizationUri;

    public String createGoogleAuthorizationUrl() {
        String state = UUID.randomUUID().toString();
        return new AuthorizationCodeRequestUrl(authorizationUri, clientId)
                .setRedirectUri(redirectUri)
                .setState(state)
                .setScopes(scopes)
                .build();
    }

}
