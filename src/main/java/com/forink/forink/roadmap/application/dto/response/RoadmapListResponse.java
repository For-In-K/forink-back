package com.forink.forink.roadmap.application.dto.response;

import com.forink.forink.roadmap.entity.RoadmapType;

public record RoadmapListResponse(

        RoadmapType roadmapType,
        int progressRatio
) {
}
