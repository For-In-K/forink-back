package com.forink.forink.auth.application;

import com.forink.forink.auth.utils.GoogleClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final GoogleClient googleClient;

    public String getGoogleLoginUrl() {
        return googleClient.createGoogleAuthorizationUrl();
    }

}
