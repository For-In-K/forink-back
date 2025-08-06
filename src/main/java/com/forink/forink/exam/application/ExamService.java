package com.forink.forink.exam.application;

import com.forink.forink.exam.application.dto.request.ExamAnswerRequest;
import com.forink.forink.exam.application.dto.response.ExamAnswerResponse;
import com.forink.forink.exam.entity.Exam;
import com.forink.forink.exam.entity.ExamStep;
import com.forink.forink.exam.entity.dao.ExamRepository;
import com.forink.forink.exam.entity.dao.ExamStepRepository;
import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.EXAM_NOT_FOUND;
import com.forink.forink.member.entity.Member;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final ExamStepRepository examStepRepository;

    public void createExam(final Member member) {
        examRepository.save(Exam.builder()
                .member(member)
                .build());
    }

    public List<ExamAnswerResponse> getExam(final Member member) {
        final List<ExamStep> steps = getExamSteps(member);

        return steps.stream()
                .map(s -> new ExamAnswerResponse(s.getStepNumber(), s.getAnswer()))
                .toList();
    }

    public List<ExamStep> getExamSteps(final Member member) {
        final Exam exam = examRepository.findByMember(member)
                .orElseThrow(() -> new BusinessException(EXAM_NOT_FOUND));
        return examStepRepository.findAllByExamOrderByStepNumberAsc(exam);
    }

    public void createExamStep(final ExamAnswerRequest request, final Integer stepNumber, final Member member) {
        final Exam exam = examRepository.findByMember(member)
                .orElseThrow(() -> new BusinessException(EXAM_NOT_FOUND));

        examStepRepository.save(ExamStep.builder()
                .exam(exam)
                .stepNumber(stepNumber)
                .answer(request.answer())
                .build());
    }

}
