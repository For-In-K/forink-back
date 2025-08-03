package com.forink.forink.roadmap.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record AiRoadmapGenerateRequest(

        @NotNull
        Long memberId,
        @NotBlank
        String memberRoleType,
        @NotNull
        Long examId,
        @NotNull
        List<Answer> responses
) {
    public record Answer(

            @NotBlank
            int stepNumber,
            @NotBlank
            int answer
    ) {}
}
