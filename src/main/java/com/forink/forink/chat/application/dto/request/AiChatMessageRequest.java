package com.forink.forink.chat.application.dto.request;

import com.forink.forink.exam.entity.ExamStep;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AiChatMessageRequest(

        @NotBlank
        String message,

        @NotNull
        List<ExamStepAnswer> examSteps
) {

    public record ExamStepAnswer(

            @NotNull
            Integer stepNumber,

            @NotNull
            Integer stepAnswer
    ) {

        public static ExamStepAnswer from(ExamStep examStep) {
            return new ExamStepAnswer(examStep.getStepNumber(), examStep.getAnswer());
        }
    }
}
