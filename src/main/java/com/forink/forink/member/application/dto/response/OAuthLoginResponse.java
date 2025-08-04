package com.forink.forink.member.application.dto.response;

import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.MemberRoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record OAuthLoginResponse(

        @NotBlank
        String token,

        @NotNull
        Long memberId,

        @NotBlank
        String email,

        @NotBlank
        String name,

        @NotNull
        MemberRoleType role
) {

    public static OAuthLoginResponse from(String token, Member member) {
        return OAuthLoginResponse.builder()
                .token(token)
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getMemberRoleType())
                .build();
    }

}
