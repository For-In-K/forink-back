package com.forink.forink.global.ai;

import com.forink.forink.chat.application.dto.request.AiChatMessageRequest;
import com.forink.forink.chat.application.dto.response.ChatAnswerResponse;
import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.AI_NETWORK_UNAVAILABLE;
import static com.forink.forink.global.error.ErrorCode.FAILED_TO_GENERATE_ROADMAP;
import static com.forink.forink.global.error.ErrorCode.FAILED_TO_GET_CHAT_ANSWER;
import com.forink.forink.roadmap.application.dto.request.AiRoadmapGenerateRequest;
import com.forink.forink.roadmap.application.dto.response.AiRoadmapGenerateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class AiClient {

    private final RestTemplate restTemplate;

    @Value("${ai.url}")
    private String aiBaseUrl;

    public AiRoadmapGenerateResponse[] generateRoadmaps(final AiRoadmapGenerateRequest request) {
        final String url = UriComponentsBuilder.fromUriString(aiBaseUrl)
                .path("/roadmaps")
                .build()
                .toUriString();
        final HttpEntity<AiRoadmapGenerateRequest> httpEntity = createRequestEntity(request);

        return executePostRequest(
                url,
                httpEntity,
                AiRoadmapGenerateResponse[].class,
                () -> new BusinessException(FAILED_TO_GENERATE_ROADMAP)
        );
    }

    public ChatAnswerResponse getChatbotResponse(final Long chatId, final AiChatMessageRequest request) {
        final String url = UriComponentsBuilder.fromUriString(aiBaseUrl)
                .path(String.format("/bots/%d/messages", chatId))
                .build()
                .toUriString();
        final HttpEntity<AiChatMessageRequest> requestEntity = createRequestEntity(request);

        return executePostRequest(
                url,
                requestEntity,
                ChatAnswerResponse.class,
                () -> new BusinessException(FAILED_TO_GET_CHAT_ANSWER)
        );
    }

    private <T> HttpEntity<T> createRequestEntity(final T body) {
        final HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(body, headers);
    }

    private <T, R> R executePostRequest(final String url,
                                        final HttpEntity<T> entity,
                                        final Class<R> responseType,
                                        final Supplier<BusinessException> nullResponseExceptionSupplier) {
        try {
            R response = restTemplate.postForObject(url, entity, responseType);
            return Optional.ofNullable(response)
                    .orElseThrow(nullResponseExceptionSupplier);
        } catch (RestClientException e) {
            throw new BusinessException(AI_NETWORK_UNAVAILABLE);
        }
    }
}
