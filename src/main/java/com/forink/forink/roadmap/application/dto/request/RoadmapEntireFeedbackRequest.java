package com.forink.forink.roadmap.application.dto.request;

import jakarta.validation.constraints.NotBlank;

public record RoadmapEntireFeedbackRequest(

        @NotBlank
        String content
) {
}
