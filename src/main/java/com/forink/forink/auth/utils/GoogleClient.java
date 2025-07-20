package com.forink.forink.auth.utils;

import com.forink.forink.auth.utils.property.GoogleOAuthProperty;
import com.forink.forink.auth.utils.property.GoogleProviderProperty;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GoogleClient {

    private final GoogleOAuthProperty googleOAuthProperty;
    private final GoogleProviderProperty googleProviderProperty;

    public String createGoogleAuthorizationUrl() {
        String state = UUID.randomUUID().toString();
        return new AuthorizationCodeRequestUrl(
                googleProviderProperty.authorizationUri(),
                googleOAuthProperty.clientId()
        )
                .setRedirectUri(googleOAuthProperty.redirectUri())
                .setScopes(googleOAuthProperty.scope())
                .setState(state)
                .build();
    }

}
