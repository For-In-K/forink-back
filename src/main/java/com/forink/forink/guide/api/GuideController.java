package com.forink.forink.guide.api;

import com.forink.forink.guide.application.GuideService;
import com.forink.forink.guide.application.dto.response.GuideListResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/guides")
public class GuideController {

    private final GuideService guideService;

    @GetMapping
    @Secured("ROLE_회원")
    public ResponseEntity<List<GuideListResponse>> getAllGuides() {
        return ResponseEntity.ok(guideService.getAllGuides());
    }

}
