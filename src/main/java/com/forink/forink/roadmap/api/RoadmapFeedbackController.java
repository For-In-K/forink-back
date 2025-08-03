package com.forink.forink.roadmap.api;

import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.application.RoadmapFeedbackService;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guides/feedbacks")
public class RoadmapFeedbackController {

    private final RoadmapFeedbackService roadmapFeedbackService;

    @GetMapping
    @Secured("ROLE_가이드")
    public ResponseEntity<List<RoadmapFeedbackListResponse>> getPreGuideRoadmapFeedbackList(@LoginMember final Member member) {
        return ResponseEntity.ok(roadmapFeedbackService.getPreGuideRoadmapFeedbackList(member.getId()));
    }


}
