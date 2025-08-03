package com.forink.forink.roadmap.application.dto.response;

import com.forink.forink.exam.entity.StatusType;

public record RoadmapTypeListResponse(

        Long roadmapId,
        String title,
        StatusType statusType
) {
}
