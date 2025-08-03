package com.forink.forink.roadmap.application.dto.request;

import com.forink.forink.roadmap.entity.RoadmapAnswerType;
import jakarta.validation.constraints.NotNull;

public record RoadmapTypeFeedbackRequest(

        @NotNull
        RoadmapAnswerType roadmapAnswerType
) {
}
