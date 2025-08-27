package com.forink.forink.guide.application.dto.response;

import com.forink.forink.resume.entity.Resume;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record GuideListResponse(

        @NotNull
        Long memberId,

        @NotBlank
        String name,

        @NotBlank
        String language,

        @NotBlank
        String nationality,

        @NotNull
        Integer guideExpCount,

        @NotBlank
        String expertise,

        @NotBlank
        String location
) {

    public static GuideListResponse from(Long memberId,
                                         Resume resume,
                                         Integer guideExpCount) {
        return GuideListResponse.builder()
                .memberId(memberId)
                .name(resume.getAnswerName())
                .language(resume.getAnswerLanguage())
                .nationality(resume.getAnswerNationality())
                .guideExpCount(guideExpCount)
                .expertise(resume.getAnswerExpertise())
                .location(resume.getAnswerLocation())
                .build();
    }
}
