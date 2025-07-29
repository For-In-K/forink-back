package com.forink.forink.global.security;

import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.dao.MemberRepository;
import com.forink.global.security.provider.GoogleUserInfo;
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
        String googleId = googleUserInfo.getGoogleId();

        Optional<Member> optionalMember = memberRepository.findByGoogleId(googleId);
        boolean isFirstLogin = optionalMember.isEmpty();

        Member member = optionalMember.orElseGet(() -> {
            String name = googleUserInfo.getName();
            String email = googleUserInfo.getEmail();
            Member newMember = Member.builder()
                    .googleId(googleId)
                    .name(name)
                    .email(email)
                    .build();
            return memberRepository.save(newMember);
        });

        return MemberPrincipal.builder()
                .member(member)
                .authorities(Collections.singleton(new SimpleGrantedAuthority(member.getMode().toString())))
                .attributes(attributes)
                .isFirstLogin(isFirstLogin)
                .build();
    }

}
