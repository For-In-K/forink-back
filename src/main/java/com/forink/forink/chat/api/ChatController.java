package com.forink.forink.chat.api;

import com.forink.forink.chat.application.ChatService;
import com.forink.forink.chat.application.dto.response.ChatCreateResponse;
import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/bots")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatCreateResponse> createChat(@LoginMember final Member member) {
        final Long chatId = chatService.createChat(member);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ChatCreateResponse.from(chatId));
    }

}
