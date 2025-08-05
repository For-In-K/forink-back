package com.forink.forink.chat.application.dto.response;

import jakarta.validation.constraints.NotBlank;

public record ChatAnswerResponse(

        @NotBlank
        String chatAnswer
) {

        public static ChatAnswerResponse from(final String chatAnswer) {
                return new ChatAnswerResponse(chatAnswer);
        }
}
