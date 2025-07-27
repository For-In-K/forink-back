package com.forink.global.security;

import com.forink.forink.member.entity.Member;
import java.util.Collection;
import java.util.Map;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.user.OAuth2User;

@Getter
@Builder
public class MemberPrincipal implements OAuth2User, UserDetails {

    private final Member member;

    private final Collection<? extends GrantedAuthority> authorities;

    private final Map<String, Object> attributes;

    private final boolean isFirstLogin;

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return member.getName();
    }

    @Override
    public Map<String, Object> getAttributes() {
        return attributes;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getName() {
        return null;
    }

    public Long getId() {
        return member.getId();
    }

}
