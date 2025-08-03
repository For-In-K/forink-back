package com.forink.forink.roadmap.application.dto.response;

import com.forink.forink.roadmap.entity.RoadmapType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoadmapListResponse(

        @NotNull
        RoadmapType roadmapType,
        @NotBlank
        int progressRatio
) {
}
