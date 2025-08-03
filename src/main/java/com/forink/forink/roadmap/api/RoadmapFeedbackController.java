package com.forink.forink.roadmap.api;

import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.application.RoadmapFeedbackService;
import com.forink.forink.roadmap.application.dto.request.RoadmapFeedbackRatingRequest;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackListResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingListResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingStatusResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
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

    @PostMapping("/{feedbackId}/ratings")
    @Secured("ROLE_가이드")
    public ResponseEntity<Void> submitPreGuideRoadmapFeedbackRating(@LoginMember final Member member,
                                                                    @PathVariable @Min(1) final Long feedbackId,
                                                                    @Valid @RequestBody final RoadmapFeedbackRatingRequest request) {
        roadmapFeedbackService.submitPreGuideRoadmapFeedbackRating(member.getId(), feedbackId, request);
        return ResponseEntity.created(URI.create("/board"))
                .build();
    }

    @GetMapping("/ratings")
    @Secured("ROLE_예비가이드")
    public ResponseEntity<List<RoadmapFeedbackRatingListResponse>> getPreGuideRoadmapFeedbackRatingList(@LoginMember final Member member) {
        return ResponseEntity.ok(roadmapFeedbackService.getPreGuideRoadmapFeedbackRatingList(member.getId()));
    }

    @GetMapping("/ratings/status")
    @Secured("ROLE_예비가이드")
    public ResponseEntity<RoadmapFeedbackRatingStatusResponse> getPreGuideRoadmapFeedbackRatingStatus(@LoginMember final Member member) {
        return ResponseEntity.ok(roadmapFeedbackService.getPreGuideRoadmapFeedbackRatingStatus(member.getId()));
    }

}
