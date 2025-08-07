package com.forink.forink.chat.application;

import com.forink.forink.chat.application.dto.request.AiChatMessageRequest;
import com.forink.forink.chat.application.dto.request.AiChatMessageRequest.ExamStepAnswer;
import com.forink.forink.chat.application.dto.response.ChatAnswerResponse;
import com.forink.forink.chat.application.dto.response.ChatCreateResponse;
import com.forink.forink.chat.entity.Chat;
import com.forink.forink.chat.entity.ChatMessage;
import com.forink.forink.chat.entity.ChatSenderType;
import static com.forink.forink.chat.entity.ChatSenderType.AI;
import static com.forink.forink.chat.entity.ChatSenderType.USER;
import com.forink.forink.chat.entity.dao.ChatMessageRepository;
import com.forink.forink.chat.entity.dao.ChatRepository;
import com.forink.forink.exam.application.ExamService;
import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.CHAT_NOT_FOUND;
import static com.forink.forink.global.error.ErrorCode.FAILED_TO_GET_CHAT_ANSWER;
import com.forink.forink.global.ai.AiClient;
import com.forink.forink.member.entity.Member;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final ExamService examService;

    private final AiClient aiClient;

    public ChatCreateResponse createChat(final Member member) {
        final Chat chat = chatRepository.save(Chat.builder()
                .member(member)
                .build());
        return ChatCreateResponse.from(chat);
    }

    @Transactional
    public ChatAnswerResponse sendMessage(final Member member,
                                          final Long chatId,
                                          final String message) {
        final Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new BusinessException(CHAT_NOT_FOUND));

        saveChatMessage(chat, message, USER);

        final List<ExamStepAnswer> examSteps = examService.getExamSteps(member)
                .stream()
                .map(AiChatMessageRequest.ExamStepAnswer::from)
                .toList();
        final AiChatMessageRequest aiChatRequest = new AiChatMessageRequest(message, examSteps);
        final ChatAnswerResponse aiChatResponse = aiClient.getChatbotResponse(chat.getId(), aiChatRequest);

        if (aiChatResponse.chatAnswer() == null) {
            throw new BusinessException(FAILED_TO_GET_CHAT_ANSWER);
        }

        saveChatMessage(chat, aiChatResponse.chatAnswer(), AI);

        return aiChatResponse;
    }

    private void saveChatMessage(final Chat chat,
                                 final String message,
                                 final ChatSenderType senderType) {
        chatMessageRepository.save(ChatMessage.builder()
                .chat(chat)
                .content(message)
                .type(senderType)
                .build());
    }

}
