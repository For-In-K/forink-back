package com.forink.forink.roadmap.application.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoadmapFeedbackRatingListResponse(

        @NotNull
        Long ratingId,

        @NotBlank
        String feedbackTitle,

        @NotNull
        Long ratingCount,

        @NotNull
        Double allAvgScore,

        @NotNull
        Double expertiseAvgScore,

        @NotNull
        Double helpAvgScore,

        @NotNull
        Double recommendAvgScore
) {
}
