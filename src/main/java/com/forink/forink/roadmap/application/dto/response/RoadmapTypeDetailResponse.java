package com.forink.forink.roadmap.application.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record RoadmapTypeDetailResponse(

        @NotBlank
        int stepNumber,
        @NotBlank
        String stepTitle,
        @NotBlank
        String stepDescription,
        @NotNull
        List<RoadmapContent> contents
) {
    public record RoadmapContent(

            @NotNull
            Long stepContentId,
            @NotBlank
            String stepContent,
            @NotBlank
            boolean isChecked
    ) {}
}
