package com.forink.forink.global.security.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record GoogleUserInfo (

        @JsonProperty("sub")
        String googleId,

        String email,

        String name
) {
}
