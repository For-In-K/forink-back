package com.forink.forink.resume.application;

import com.forink.forink.member.entity.Member;
import com.forink.forink.resume.application.dto.response.ResumeResponse;
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

    public ResumeResponse getResume(final Member member) {
        Resume resume = resumeRepository.findByMember(member)
                .orElseThrow(() -> new RuntimeException("Member not found"));
        return ResumeResponse.from(resume);
    }

}
