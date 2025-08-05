package com.forink.forink.chat.application;

import com.forink.forink.chat.entity.Chat;
import com.forink.forink.chat.entity.dao.ChatRepository;
import com.forink.forink.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    public Long createChat(final Member member) {
        final Chat chat = chatRepository.save(Chat.builder()
                .member(member)
                .build());
        return chat.getId();
    }

}
