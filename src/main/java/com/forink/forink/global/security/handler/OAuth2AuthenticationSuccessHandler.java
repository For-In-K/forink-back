package com.forink.forink.global.security.handler;

import com.forink.forink.member.entity.Member;
import com.forink.global.security.MemberPrincipal;
import com.forink.global.security.util.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final JwtTokenProvider jwtTokenProvider;

    private static final String ACCESS_TOKEN_FIELD = "accessToken";

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication) throws IOException {
        MemberPrincipal principal = (MemberPrincipal) authentication.getPrincipal();
        Member member = principal.getMember();

        String accessToken = jwtTokenProvider.generateAccessToken(member.getId());

        Cookie cookie = new Cookie(ACCESS_TOKEN_FIELD, accessToken);
        cookie.setHttpOnly(true);
        cookie.setPath("/");

        response.addCookie(cookie);

        if (principal.isFirstLogin()) {
            response.sendRedirect("/select");
            return;
        }

        response.sendRedirect("/");
    }

}
