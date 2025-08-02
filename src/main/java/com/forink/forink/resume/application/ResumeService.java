package com.forink.forink.resume.application;

import com.forink.forink.member.entity.Member;
import com.forink.forink.resume.application.dto.response.ResumeResponse;
import com.forink.forink.resume.domain.ResumeStepUpdater;
import com.forink.forink.resume.entity.Resume;
import com.forink.forink.resume.entity.dao.ResumeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ResumeService {

    private final ResumeRepository resumeRepository;

    @Transactional
    public void createResume(final Member member) {
        resumeRepository.save(Resume.builder()
                .member(member)
                .build());
    }

    @Transactional
    public void submitResume(final Long memberId) {
        Resume resume = resumeRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        Member member = resume.getMember();

        resume.complete();
        member.qualifyAsPreGuide();
    }

    public ResumeResponse getResume(final Long memberId) {
        Resume resume = resumeRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        return ResumeResponse.from(resume);
    }

    @Transactional
    public void updateResumeByStep(final Long memberId,
                                   final Integer stepNumber,
                                   final String answer) {
        Resume resume = resumeRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        ResumeStepUpdater updater = ResumeStepUpdater.from(stepNumber);
        updater.update(resume, answer);
    }

}
