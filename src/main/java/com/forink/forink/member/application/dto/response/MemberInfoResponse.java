package com.forink.forink.member.application.dto.response;

import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.MemberRoleType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record MemberInfoResponse(

        @NotNull
        Long memberId,

        @NotBlank
        String email,

        @NotBlank
        String name,

        @NotNull
        MemberRoleType role,

        @NotNull
        Integer point
) {

    public static MemberInfoResponse from(Member member) {
        return MemberInfoResponse.builder()
                .memberId(member.getId())
                .email(member.getEmail())
                .name(member.getName())
                .role(member.getMemberRoleType())
                .point(member.getPoint())
                .build();
    }
}
