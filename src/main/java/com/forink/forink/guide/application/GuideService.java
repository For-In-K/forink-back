package com.forink.forink.guide.application;

import com.forink.forink.guide.application.dto.response.GuideListResponse;
import static com.forink.forink.member.entity.MemberRoleType.ROLE_가이드;
import com.forink.forink.resume.entity.dao.ResumeRepository;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GuideService {

    private final ResumeRepository resumeRepository;

    private Random random;

    @PostConstruct
    public void init() {
        random = new Random();
    }

    public List<GuideListResponse> getAllGuides() {
        return resumeRepository.findAllByMember_MemberRoleType(ROLE_가이드)
                .stream()
                .map(resume -> GuideListResponse.from(
                        resume.getMember().getId(),
                        resume,
                        getFakeGuideExpCount()
                )).toList();
    }

    private Integer getFakeGuideExpCount() {
        return random.nextInt(10);
    }

}
