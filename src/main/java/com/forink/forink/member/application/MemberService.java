package com.forink.forink.member.application;

import com.forink.forink.global.security.util.GoogleClient;
import com.forink.forink.member.entity.dao.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private final GoogleClient googleClient;

    public String getGoogleLoginRedirectURL() {
        return googleClient.createGoogleAuthorizationUrl();
    }

}
