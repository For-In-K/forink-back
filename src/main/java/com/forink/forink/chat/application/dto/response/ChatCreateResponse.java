package com.forink.forink.chat.application.dto.response;

import jakarta.validation.constraints.NotNull;

public record ChatCreateResponse(

        @NotNull
        Long chatId
) {

    public static ChatCreateResponse from(Long chatId) {
        return new ChatCreateResponse(chatId);
    }
}
