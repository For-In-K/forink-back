package com.forink.forink.global.security.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.FAILED_TO_GET_ACCESS_TOKEN;
import static com.forink.forink.global.error.ErrorCode.FAILED_TO_GET_OAUTH_USERINFO;
import static com.forink.forink.global.error.ErrorCode.OAUTH_AUTHORIZATION_FAILED;
import static com.forink.forink.global.error.ErrorCode.OAUTH_NETWORK_UNAVAILABLE;
import com.forink.forink.global.security.data.OAuthConstants;
import com.forink.forink.global.security.dto.GoogleOAuthToken;
import com.forink.forink.global.security.dto.GoogleUserInfo;
import com.google.api.client.auth.oauth2.AuthorizationCodeRequestUrl;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class GoogleClient {

    @Value("${spring.security.oauth2.client.google.client-id}")
    private String clientId;

    @Value("${spring.security.oauth2.client.google.client-secret}")
    private String clientSecret;

    @Value("${spring.security.oauth2.client.google.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.google.scope}")
    private List<String> scopes;

    @Value("${spring.security.oauth2.client.google.authorization-uri}")
    private String authorizationUri;

    private final RestTemplate restTemplate;

    private final ObjectMapper objectMapper;

    public String createGoogleAuthorizationUrl() {
        String state = UUID.randomUUID().toString();
        return new AuthorizationCodeRequestUrl(authorizationUri, clientId)
                .setRedirectUri(redirectUri)
                .setState(state)
                .setScopes(scopes)
                .build();
    }

    public GoogleUserInfo getGoogleUserInfoByCode(String code) {
        ResponseEntity<String> tokenResponse = requestAccessTokenByCode(code);
        GoogleOAuthToken googleOAuthToken = extractAccessToken(tokenResponse);
        ResponseEntity<String> userInfoResponse = requestGoogleUserInfoByToken(googleOAuthToken);
        return extractGoogleUserInfo(userInfoResponse);
    }

    private ResponseEntity<String> requestAccessTokenByCode(String code) {
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", code);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", OAuthConstants.GRANT_TYPE);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(
                    OAuthConstants.GOOGLE_TOKEN_REQUEST_URL,
                    requestEntity,
                    String.class
            );
            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                return responseEntity;
            }
            throw new BusinessException(OAUTH_AUTHORIZATION_FAILED);
        } catch (RestClientException e) {
            throw new BusinessException(OAUTH_NETWORK_UNAVAILABLE);
        }
    }

    private GoogleOAuthToken extractAccessToken(ResponseEntity<String> response) {
        try {
            GoogleOAuthToken googleOAuthToken = objectMapper.readValue(response.getBody(), GoogleOAuthToken.class);
            if (googleOAuthToken == null || googleOAuthToken.token() == null) {
                throw new BusinessException(FAILED_TO_GET_ACCESS_TOKEN);
            }
            return googleOAuthToken;
        } catch (JsonProcessingException e) {
            throw new BusinessException(FAILED_TO_GET_ACCESS_TOKEN);
        }
    }

    private ResponseEntity<String> requestGoogleUserInfoByToken(GoogleOAuthToken googleOAuthToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(googleOAuthToken.token());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(headers);

        try {
            return restTemplate.exchange(
                    OAuthConstants.GOOGLE_USERINFO_REQUEST_URL,
                    HttpMethod.GET,
                    requestEntity,
                    String.class
            );
        } catch (RestClientException e) {
            throw new BusinessException(OAUTH_NETWORK_UNAVAILABLE);
        }
    }

    private GoogleUserInfo extractGoogleUserInfo(ResponseEntity<String> response) {
        try {
            GoogleUserInfo googleUserInfo = objectMapper.readValue(response.getBody(), GoogleUserInfo.class);
            if (googleUserInfo == null) {
                throw new BusinessException(FAILED_TO_GET_OAUTH_USERINFO);
            }
            return googleUserInfo;
        } catch (JsonProcessingException e) {
            throw new BusinessException(FAILED_TO_GET_OAUTH_USERINFO);
        }
    }

}
