package com.forink.forink.roadmap.application.dto.response;

public record RoadmapContentResponse(

        Long stepContentId,
        String stepContent,
        boolean isChecked
) {
}
