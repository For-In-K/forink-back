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
import com.forink.forink.member.entity.Member;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatRepository chatRepository;

    private final ChatMessageRepository chatMessageRepository;

    private final ExamService examService;

    private final RestTemplate restTemplate;

    private static final String AI_CHAT_URL = "https://temp-ai-service.com/bots";

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
                .orElseThrow(() -> new IllegalArgumentException("chat not found"));

        saveChatMessage(chat, message, USER);

        final List<ExamStepAnswer> examSteps = examService.getExamSteps(member)
                .stream()
                .map(AiChatMessageRequest.ExamStepAnswer::from)
                .toList();
        final AiChatMessageRequest aiChatRequest = new AiChatMessageRequest(message, examSteps);
        final ChatAnswerResponse aiChatResponse = callAiChatService(aiChatRequest, chat.getId());

        if (aiChatResponse.chatAnswer() == null) {
            throw new RuntimeException("챗봇 답변이 없습니다.");
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

    private ChatAnswerResponse callAiChatService(final AiChatMessageRequest request, final Long chatId) {
        String url = UriComponentsBuilder.fromUriString(AI_CHAT_URL)
                .path(String.format("/%d/messages", chatId))
                .build()
                .toUriString();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AiChatMessageRequest> requestEntity = new HttpEntity<>(request, headers);

        try {
            ResponseEntity<ChatAnswerResponse> response = restTemplate.postForEntity(
                    url,
                    requestEntity,
                    ChatAnswerResponse.class
            );
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                return response.getBody();
            }
            throw new RuntimeException("챗봇 답변을 수신할 수 없습니다.");
        } catch (RestClientException e) {
            throw new RuntimeException("AI 서버에 요청할 수 없습니다.");
        }
    }

}
