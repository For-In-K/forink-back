package com.forink.forink.roadmap.application.dto.response;

import com.forink.forink.exam.entity.StatusType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RoadmapTypeListResponse(

        @NotNull
        Long roadmapId,
        @NotBlank
        String title,
        @NotNull
        StatusType statusType
) {
}
