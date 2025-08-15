package com.forink.forink.roadmap.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.application.RoadmapService;
import com.forink.forink.roadmap.application.dto.request.RoadmapEntireFeedbackRequest;
import com.forink.forink.roadmap.application.dto.request.RoadmapTypeFeedbackRequest;
import com.forink.forink.roadmap.application.dto.response.RoadmapListResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapTypeDetailResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapTypeListResponse;
import com.forink.forink.roadmap.entity.RoadmapType;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/roadmaps")
@Secured("ROLE_회원")
public class RoadmapController {

    private final RoadmapService roadmapService;

    @GetMapping
    public ResponseEntity<List<RoadmapListResponse>> getAllRoadmapList(@LoginMember final Member member) {
        return ResponseEntity.ok(roadmapService.getAllRoadmapList(member));
    }

    @GetMapping("/types/{roadmapType}")
    public ResponseEntity<List<RoadmapTypeListResponse>> getRoadmapTypeList(@PathVariable final RoadmapType roadmapType,
                                                                            @LoginMember final Member member) {
        return ResponseEntity.ok(roadmapService.getRoadmapTypeList(roadmapType, member));
    }

    @GetMapping("/{roadmapId}")
    public ResponseEntity<List<RoadmapTypeDetailResponse>> getRoadmapTypeDetails(@PathVariable final Long roadmapId,
                                                                                 @LoginMember final Member member) {
        return ResponseEntity.ok(roadmapService.getRoadmapTypeDetails(roadmapId, member));
    }

    @PatchMapping("/roadmapStepContents/{roadmapStepContentId}")
    public ResponseEntity<Void> updateRoadmapIsChecked(@PathVariable final Long roadmapStepContentId,
                                                       @LoginMember final Member member) {
        roadmapService.updateRoadmapIsChecked(roadmapStepContentId, member);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/roadmapSteps/{roadmapStepId}")
    public ResponseEntity<Void> createRoadmapTypeFeedback(@PathVariable final Long roadmapStepId,
                                                          @Valid @RequestBody final RoadmapTypeFeedbackRequest request,
                                                          @LoginMember final Member member) {
        roadmapService.createRoadmapTypeFeedback(roadmapStepId, request, member);
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping("/{roadmapId}/feedback")
    public ResponseEntity<Void> createRoadmapEntireFeedback(@PathVariable final Long roadmapId,
                                                            @Valid @RequestBody final RoadmapEntireFeedbackRequest request,
                                                            @LoginMember final Member member) {
        roadmapService.createRoadmapEntireFeedback(roadmapId, request, member);
        return ResponseEntity.status(CREATED).build();
    }

    @PostMapping
    public ResponseEntity<Void> createRoadmaps(@LoginMember final Member member) {
        roadmapService.createRoadmaps(member);
        return ResponseEntity.status(CREATED).build();
    }
}
