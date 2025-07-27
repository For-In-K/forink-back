package com.forink.global.security.provider;

import java.util.Map;
import lombok.Getter;

@Getter
public class GoogleUserInfo {

    private final String googleId;

    private final String email;

    private final String name;

    public GoogleUserInfo(Map<String, Object> attributes) {
        this.googleId = (String) attributes.get("sub");
        this.email = (String) attributes.get("email");
        this.name = (String) attributes.get("name");
    }

}
