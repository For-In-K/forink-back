package com.forink.forink.exam.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.forink.forink.exam.application.ExamService;
import com.forink.forink.exam.application.dto.request.ExamAnswerRequest;
import com.forink.forink.exam.application.dto.response.ExamAnswerResponse;
import com.forink.forink.global.security.annotation.LoginMember;
import com.forink.forink.member.entity.Member;
import jakarta.validation.Valid;
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
@RequestMapping("/exams")
@Secured("ROLE_회원")
public class ExamController {

    private final ExamService examService;

    @PostMapping
    public ResponseEntity<Void> createExam(@LoginMember final Member member) {
        examService.createExam(member);
        return ResponseEntity.status(CREATED)
                .location(URI.create("/exams"))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ExamAnswerResponse>> getExam(@LoginMember final Member member) {
        return ResponseEntity.ok(examService.getExam(member));
    }

    @PostMapping("/steps/{stepNumber}")
    public ResponseEntity<Void> createExamStep(@Valid @RequestBody final ExamAnswerRequest request,
                                               @PathVariable final Integer stepNumber,
                                               @LoginMember final Member member) {
        examService.createExamStep(request, stepNumber, member);
        return ResponseEntity.status(CREATED).build();
    }
}
