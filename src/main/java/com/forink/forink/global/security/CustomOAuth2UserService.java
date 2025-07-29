package com.forink.forink.global.security;

import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.dao.MemberRepository;
import com.forink.forink.global.security.data.MemberPrincipal;
import com.forink.forink.global.security.data.GoogleUserInfo;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(userRequest);
        Map<String, Object> attributes = oAuth2User.getAttributes();

        GoogleUserInfo googleUserInfo = new GoogleUserInfo(attributes);
        String email = googleUserInfo.getEmail();

        Optional<Member> optionalMember = memberRepository.findByEmail(email);
        boolean isFirstLogin = optionalMember.isEmpty();

        Member member = optionalMember.orElseGet(() -> {
            String googleId = googleUserInfo.getGoogleId();
            String name = googleUserInfo.getName();
            Member newMember = Member.builder()
                    .googleId(googleId)
                    .name(name)
                    .email(email)
                    .build();
            return memberRepository.save(newMember);
        });

        return MemberPrincipal.builder()
                .member(member)
                .authorities(Collections.singleton(new SimpleGrantedAuthority(member.getMemberRoleType().toString())))
                .attributes(attributes)
                .isFirstLogin(isFirstLogin)
                .build();
    }

}
