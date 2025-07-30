package com.forink.forink.member.application;

import com.forink.forink.global.security.dto.GoogleUserInfo;
import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.MemberRoleType;
import com.forink.forink.member.entity.dao.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRegistrationService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member registerNewGoogleMember(GoogleUserInfo googleUserInfo) {
        Member member = Member.builder()
                .email(googleUserInfo.email())
                .googleId(googleUserInfo.googleId())
                .name(googleUserInfo.name())
                .memberRoleType(MemberRoleType.ROLE_회원)
                .build();
        return memberRepository.save(member);
    }

}
