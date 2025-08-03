package com.forink.forink.roadmap.api;

import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.application.RoadmapService;
import com.forink.forink.roadmap.application.dto.response.RoadmapListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/roadmaps")
public class RoadmapController {

    private final RoadmapService roadmapService;

    @GetMapping
    public ResponseEntity<List<RoadmapListResponse>> getAllRoadmapList(@LoginMember final Member member) {
        return ResponseEntity.ok(roadmapService.getAllRoadmapList(member));
    }
}
