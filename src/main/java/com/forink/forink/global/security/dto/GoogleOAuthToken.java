package com.forink.forink.global.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleOAuthToken(

        @JsonProperty("access_token")
        String token
) {
}
