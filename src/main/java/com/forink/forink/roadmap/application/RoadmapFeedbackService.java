package com.forink.forink.roadmap.application;

import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackListResponse;
import com.forink.forink.roadmap.entity.dao.RoadmapCompletionFeedbackRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadmapFeedbackService {

    private final RoadmapCompletionFeedbackRepository roadmapFeedbackRepository;

    public List<RoadmapFeedbackListResponse> getPreGuideRoadmapFeedbackList(final Long memberId) {
        return roadmapFeedbackRepository.findUnratedAllByRaterId(memberId)
                .stream()
                .map(RoadmapFeedbackListResponse::from)
                .toList();
    }

}
