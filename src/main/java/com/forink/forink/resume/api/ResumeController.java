package com.forink.forink.resume.api;

import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import com.forink.forink.resume.application.ResumeService;
import com.forink.forink.resume.application.dto.request.ResumeAnswerRequest;
import com.forink.forink.resume.application.dto.response.ResumeResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("/guides/resume")
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping
    public ResponseEntity<Void> createResume(@LoginMember final Member member) {
        resumeService.createResume(member);
        return ResponseEntity.created(URI.create("/guides/resume/1"))
                .build();
    }

    @GetMapping
    public ResponseEntity<ResumeResponse> getResume(@LoginMember final Member member) {
        return ResponseEntity.ok(resumeService.getResume(member));
    }

    @PatchMapping("/steps/{stepNumber}")
    public ResponseEntity<Void> updateResume(@LoginMember final Member member,
                                             @PathVariable @Min(1) final Integer stepNumber,
                                             @Valid @RequestBody final ResumeAnswerRequest request) {
        resumeService.updateResumeByStep(member, stepNumber, request);
        return ResponseEntity.noContent().build();
    }

}
