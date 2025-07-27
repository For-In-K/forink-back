package com.forink.forink.exam.application;

import static com.forink.forink.member.entity.MemberRoleType.ROLE_회원;

import com.forink.forink.exam.entity.Exam;
import com.forink.forink.exam.entity.dao.ExamRepository;
import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.dao.MemberRepository;
import java.util.ArrayList;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class ExamService {

    private final ExamRepository examRepository;
    private final MemberRepository memberRepository;

    public void createExam() {
        // todo : 로그인 기능 개발 완료 시 수정 필요
        final Member tempMember = createFakeMember();
        examRepository.save(Exam.builder()
                .member(tempMember)
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
