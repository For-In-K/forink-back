package com.forink.forink.member.application;

import com.forink.forink.global.security.dto.GoogleUserInfo;
import com.forink.forink.global.security.util.GoogleClient;
import com.forink.forink.global.security.util.JwtTokenProvider;
import com.forink.forink.member.application.dto.response.OAuthLoginResponse;
import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.dao.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final MemberRegistrationService memberRegistrationService;

    private final GoogleClient googleClient;

    private final JwtTokenProvider jwtTokenProvider;

    public String getGoogleLoginRedirectURL() {
        return googleClient.createGoogleAuthorizationUrl();
    }

    @Transactional
    public OAuthLoginResponse processGoogleOAuthCallback(final String code) {
        final GoogleUserInfo googleUserInfo = googleClient.getGoogleUserInfoByCode(code);
        return memberRepository.findByEmail(googleUserInfo.email())
                .map(this::loginExistingMember)
                .orElseGet(() -> registerAndLoginGoogleMember(googleUserInfo));
    }

    private OAuthLoginResponse registerAndLoginGoogleMember(final GoogleUserInfo googleUserInfo) {
        final Member newMember = memberRegistrationService.registerNewGoogleMember(googleUserInfo);
        return loginExistingMember(newMember);
    }

    private OAuthLoginResponse loginExistingMember(final Member member) {
        final String token = jwtTokenProvider.generateAccessToken(
                String.valueOf(member.getId()),
                member.getMemberRoleType().name(),
                member.getEmail()
        );
        return OAuthLoginResponse.from(token, member);
    }

}
