package com.forink.forink.auth.utils.property;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.security.oauth2.client.provider.google")
public record GoogleProviderProperty(
        String authorizationUri,
        String tokenUri,
        String userInfoUri
) {

}
