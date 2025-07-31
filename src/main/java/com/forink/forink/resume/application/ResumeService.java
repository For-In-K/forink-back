package com.forink.forink.resume.application;

import com.forink.forink.member.entity.Member;
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
    public void createResume(Member member) {
        resumeRepository.save(Resume.builder()
                .member(member)
                .build());
    }

}
