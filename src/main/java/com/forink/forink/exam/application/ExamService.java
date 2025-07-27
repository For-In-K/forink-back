package com.forink.forink.exam.application;

import static com.forink.forink.member.entity.MemberRoleType.ROLE_회원;

import com.forink.forink.exam.application.dto.request.ExamAnswerRequest;
import com.forink.forink.exam.application.dto.response.ExamAnswerResponse;
import com.forink.forink.exam.entity.Exam;
import com.forink.forink.exam.entity.ExamStep;
import com.forink.forink.exam.entity.dao.ExamRepository;
import com.forink.forink.exam.entity.dao.ExamStepRepository;
import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.dao.MemberRepository;
import java.util.ArrayList;
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
    private final MemberRepository memberRepository;

    public void createExam() {
        // todo : 로그인 기능 개발 완료 시 수정 필요
        final Member tempMember = createFakeMember();
        examRepository.save(Exam.builder()
                .member(tempMember)
                .build());
    }

    public List<ExamAnswerResponse> getExam() {
        // todo : 로그인 기능 개발 완료 시 수정 필요
        final Member tempMember = createFakeMember();
        final Exam exam = examRepository.findByMember(tempMember).orElseThrow();
        final List<ExamStep> steps = examStepRepository.findAllByExamOrderByStepNumberAsc(exam);

        return steps.stream()
                .map(s -> new ExamAnswerResponse(s.getStepNumber(), s.getAnswer()))
                .toList();
    }

    public void createExamStep(final ExamAnswerRequest request, final Integer stepNumber) {
        // todo : 로그인 기능 개발 완료 시 수정 필요
        final Member tempMember = createFakeMember();
        final Exam exam = examRepository.findByMember(tempMember).orElseThrow();

        examStepRepository.save(ExamStep.builder()
                .exam(exam)
                .stepNumber(stepNumber)
                .answer(request.answer())
                .build());
    }

    private Member createFakeMember() {
        return memberRepository.save(Member.builder()
                .email("temp@pusan.ac.kr")
                .google_id("tempId")
                .name("tempName")
                .mode(ROLE_회원)
                .roadmaps(new ArrayList<>())
                .build());
    }
}
