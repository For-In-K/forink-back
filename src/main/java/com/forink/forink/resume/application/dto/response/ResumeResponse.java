package com.forink.forink.resume.application.dto.response;

import com.forink.forink.resume.entity.Resume;
import lombok.Builder;

@Builder
public record ResumeResponse(

        String name,

        String age,

        String nationality,

        String language,

        String expertise,

        String link
) {

    public static ResumeResponse from(Resume resume) {
        return ResumeResponse.builder()
                .name(resume.getAnswerName())
                .age(resume.getAnswerAge())
                .nationality(resume.getAnswerNationality())
                .language(resume.getAnswerLanguage())
                .expertise(resume.getAnswerExpertise())
                .link(resume.getAnswerLink())
                .build();
    }

}
