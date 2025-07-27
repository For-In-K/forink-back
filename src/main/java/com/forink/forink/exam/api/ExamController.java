package com.forink.forink.exam.api;

import static org.springframework.http.HttpStatus.CREATED;

import com.forink.forink.exam.application.ExamService;
import com.forink.forink.exam.application.dto.response.ExamAnswerResponse;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/exams")
public class ExamController {

    private final ExamService examService;

    // todo: member 정보 받아오는 로직 필요
    @PostMapping
    public ResponseEntity<Void> createExam() {
        examService.createExam();
        return ResponseEntity.status(CREATED)
                .location(URI.create("/exams"))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<ExamAnswerResponse>> getExam() {
        return ResponseEntity.ok(examService.getExam());
    }
}
