package com.forink.forink.roadmap.application.dto.response;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;


public record AiRoadmapGenerateResponse(

        @NotBlank
        String type,
        @NotNull
        Integer order,
        @NotBlank
        String title,
        @NotNull
        List<AiStep> steps
) {
    public record AiStep(

            @NotNull
            Integer stepNumber,
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
