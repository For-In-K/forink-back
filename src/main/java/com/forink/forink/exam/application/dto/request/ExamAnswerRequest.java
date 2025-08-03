package com.forink.forink.exam.application.dto.request;

import jakarta.validation.constraints.NotNull;

public record ExamAnswerRequest(

        @NotNull
        Integer answer
) {
}
