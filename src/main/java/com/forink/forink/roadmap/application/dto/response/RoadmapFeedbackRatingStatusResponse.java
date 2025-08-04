package com.forink.forink.roadmap.application.dto.response;

import com.forink.forink.roadmap.entity.RoadmapFeedbackRatingStatusType;
import jakarta.validation.constraints.NotNull;

public record RoadmapFeedbackRatingStatusResponse(

        @NotNull
        RoadmapFeedbackRatingStatusType ratingStatus
) {
}
