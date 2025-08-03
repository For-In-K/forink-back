package com.forink.forink.roadmap.application.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RoadmapFeedbackRatingRequest(

        @NotNull
        @Min(1)
        @Max(5)
        Integer expertiseScore,

        @NotNull
        @Min(1)
        @Max(5)
        Integer helpScore,

        @NotNull
        @Min(1)
        @Max(5)
        Integer recommendScore
) {
}
