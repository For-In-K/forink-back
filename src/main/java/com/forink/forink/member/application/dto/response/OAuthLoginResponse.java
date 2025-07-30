package com.forink.forink.member.application.dto.response;

import com.forink.forink.member.entity.Member;
import lombok.Builder;

@Builder
public record OAuthLoginResponse(

        String token,

        Long memberId,

        String email,

        String name,

        String role
) {

    public static OAuthLoginResponse from(String token, Member member) {
        return OAuthLoginResponse.builder()
                .token(token)
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getMemberRoleType().name())
                .build();
    }

}
