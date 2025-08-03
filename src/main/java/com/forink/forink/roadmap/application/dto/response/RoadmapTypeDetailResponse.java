package com.forink.forink.roadmap.application.dto.response;

import java.util.List;

public record RoadmapTypeDetailResponse(

        int stepNumber,
        String stepTitle,
        String stepDescription,
        List<RoadmapContentResponse> contents
) {
}
