package com.forink.forink.roadmap.application.dto.response;

import java.util.List;


public record AiRoadmapGenerateResponse(
        String type,
        int order,
        String title,
        List<AiStep> steps
) {
    public record AiStep(
            int stepNumber,
            String stepTitle,
            String stepDescription,
            List<AiContent> contents
    ) {}

    public record AiContent(
            String stepContent
    ) {}
}
