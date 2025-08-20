package com.forink.forink.member.api;

import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.application.MemberService;
import com.forink.forink.member.application.dto.response.MemberInfoResponse;
import com.forink.forink.member.application.dto.response.OAuthLoginResponse;
import com.forink.forink.member.entity.Member;
import jakarta.validation.constraints.NotBlank;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/oauth/google")
    public ResponseEntity<Void> redirectToGoogleOAuth() {
        String redirectURL = memberService.getGoogleLoginRedirectURL();
        return ResponseEntity.status(HttpStatus.FOUND)
                .location(URI.create(redirectURL))
                .build();
    }

    @GetMapping("/oauth/google/callback")
    public ResponseEntity<OAuthLoginResponse> handleGoogleOAuthCallback(@NotBlank @RequestParam("code") final String code) {
        return ResponseEntity.ok(memberService.processGoogleOAuthCallback(code));
    }

    @GetMapping("/members/me")
    public ResponseEntity<MemberInfoResponse> getMyInfo(@LoginMember final Member member) {
        return ResponseEntity.ok(MemberInfoResponse.from(member));
    }

}
