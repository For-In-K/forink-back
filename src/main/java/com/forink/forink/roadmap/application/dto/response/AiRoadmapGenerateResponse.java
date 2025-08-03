package com.forink.forink.roadmap.application.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;


public record AiRoadmapGenerateResponse(

        @NotBlank
        String type,
        @NotBlank
        int order,
        @NotBlank
        String title,
        @NotNull
        List<AiStep> steps
) {
    public record AiStep(

            @NotBlank
            int stepNumber,
            @NotBlank
            String stepTitle,
            @NotBlank
            String stepDescription,
            @NotNull
            List<AiContent> contents
    ) {}

    public record AiContent(

            @NotBlank
            String stepContent
    ) {}
}
