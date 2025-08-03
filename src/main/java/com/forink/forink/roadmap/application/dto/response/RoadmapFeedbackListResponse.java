package com.forink.forink.roadmap.application.dto.response;

import com.forink.forink.roadmap.entity.RoadmapCompletionFeedback;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RoadmapFeedbackListResponse(

        @NotNull
        Long feedbackId,

        @NotBlank
        String feedbackTitle,

        @NotBlank
        String feedbackContent
) {

        public static RoadmapFeedbackListResponse from(RoadmapCompletionFeedback feedback) {
                return RoadmapFeedbackListResponse.builder()
                        .feedbackId(feedback.getId())
                        .feedbackTitle(feedback.getRoadmap().getTitle())
                        .feedbackContent(feedback.getContent())
                        .build();
        }
}
