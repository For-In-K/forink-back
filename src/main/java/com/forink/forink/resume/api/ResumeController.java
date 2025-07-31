package com.forink.forink.resume.api;

import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import com.forink.forink.resume.application.ResumeService;
import com.forink.forink.resume.application.dto.response.ResumeResponse;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
