package com.forink.forink.auth.utils.property;

import java.util.List;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.registration.google")
public record GoogleOAuthProperty(
        String clientId,
        String clientSecret,
        String redirectUri,
        List<String> scope
) {

}
