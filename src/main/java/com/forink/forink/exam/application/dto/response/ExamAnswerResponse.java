package com.forink.forink.exam.application.dto.response;

import jakarta.validation.constraints.NotNull;

public record ExamAnswerResponse(

        @NotNull
        Integer stepNumber,
        @NotNull
        Integer answer
) {
}
