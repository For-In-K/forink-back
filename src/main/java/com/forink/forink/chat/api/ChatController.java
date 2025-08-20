package com.forink.forink.chat.api;

import com.forink.forink.chat.application.ChatService;
import com.forink.forink.chat.application.dto.request.ChatMessageRequest;
import com.forink.forink.chat.application.dto.response.ChatAnswerResponse;
import com.forink.forink.chat.application.dto.response.ChatCreateResponse;
import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/bots")
public class ChatController {

    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<ChatCreateResponse> createChat(@LoginMember final Member member) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(chatService.createChat(member));
    }

    @PostMapping("/{botId}/messages")
    public ResponseEntity<ChatAnswerResponse> sendMessage(@LoginMember final Member member,
                                                          @PathVariable @Min(1) final Long botId,
                                                          @Valid @RequestBody final ChatMessageRequest request) {
        return ResponseEntity.ok(chatService.sendMessage(member, botId, request.message()));
    }

}
