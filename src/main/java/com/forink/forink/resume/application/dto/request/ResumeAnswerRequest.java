package com.forink.forink.resume.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ResumeAnswerRequest(

        @NotBlank
        String answer
) {
}
