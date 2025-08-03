package com.forink.forink.roadmap.application.dto.request;

import java.util.List;

public record AiRoadmapGenerateRequest(

        Long memberId,
        String memberRoleType,
        Long examId,
        List<Answer> responses
) {
    public record Answer(

            int stepNumber,
            int answer
    ) {}
}
