package com.forink.forink.chat.application.dto.response;

import com.forink.forink.chat.entity.Chat;
import jakarta.validation.constraints.NotNull;

public record ChatCreateResponse(

        @NotNull
        Long chatId
) {

    public static ChatCreateResponse from(Chat chat) {
        return new ChatCreateResponse(chat.getId());
    }
}
