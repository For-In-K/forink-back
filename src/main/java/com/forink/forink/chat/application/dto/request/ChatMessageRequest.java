package com.forink.forink.chat.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record ChatMessageRequest(

        @NotBlank
        String message
) {
}
