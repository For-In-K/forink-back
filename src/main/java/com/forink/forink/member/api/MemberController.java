package com.forink.forink.member.api;

import com.forink.forink.member.application.MemberService;
import com.forink.forink.member.application.dto.response.OAuthLoginResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/oauth/google")
    public ResponseEntity<Void> redirectToGoogleOAuth() {
        String redirectURL = memberService.getGoogleLoginRedirectURL();

        URI redirectUri = UriComponentsBuilder.fromUriString(redirectURL)
                .encode()
                .build()
                .toUri();

        return ResponseEntity.status(HttpStatus.FOUND)
                .location(redirectUri)
                .build();
    }

    @GetMapping("/oauth/google/callback")
    public ResponseEntity<OAuthLoginResponse> handleGoogleOAuthCallback(@RequestParam String code) {
        return ResponseEntity.ok(memberService.processGoogleOAuthCallback(code));
    }

}
