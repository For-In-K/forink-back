package com.forink.forink.global.security.util;

import static com.forink.forink.global.security.data.JwtConstants.AUTH_TYPE;

import com.forink.forink.global.security.MemberPrincipalService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import jakarta.servlet.http.HttpServletRequest;
import java.security.Key;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    @Value("${spring.jwt.secret}")
    private String secret;

    private static final String EMAIL = "email";

    private Key secretKey;

    private final MemberPrincipalService memberPrincipalService;

    @PostConstruct
    protected void init() {
        this.secretKey = Keys.hmacShaKeyFor(secret.getBytes());
    }

    public String generateAccessToken(String userPk, String email) {
        Date now = new Date();

        return Jwts.builder()
                .setSubject(userPk)
                .claim(EMAIL, email)
                .setIssuedAt(now)
                .signWith(secretKey, SignatureAlgorithm.HS256)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        String memberId = getClaim(token).getSubject();
        UserDetails userDetails = memberPrincipalService.loadUserByUsername(memberId);
        return new UsernamePasswordAuthenticationToken(
                userDetails,
                "",
                userDetails.getAuthorities()
        );
    }

    private Claims getClaim(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (bearerToken != null && bearerToken.startsWith(AUTH_TYPE)) {
            return bearerToken.substring(AUTH_TYPE.length());
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
        return true;
    }

}
