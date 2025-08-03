package com.forink.forink.roadmap.application.dto.response;

import com.forink.forink.roadmap.entity.RoadmapFeedbackRatingStatusType;
import jakarta.validation.constraints.NotBlank;

public record RoadmapFeedbackRatingStatusResponse(

        @NotBlank
        RoadmapFeedbackRatingStatusType ratingStatus
) {
}
