package com.forink.forink.roadmap.application;

import com.forink.forink.roadmap.application.dto.request.RoadmapFeedbackRatingRequest;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackListResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingListResponse;
import com.forink.forink.roadmap.entity.RoadmapCompletionFeedback;
import com.forink.forink.roadmap.entity.RoadmapCompletionFeedbackRating;
import com.forink.forink.roadmap.entity.dao.RoadmapCompletionFeedbackRatingRepository;
import com.forink.forink.roadmap.entity.dao.RoadmapCompletionFeedbackRepository;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadmapFeedbackService {

    private final RoadmapCompletionFeedbackRepository roadmapFeedbackRepository;

    private final RoadmapCompletionFeedbackRatingRepository roadmapFeedbackRatingRepository;

    public List<RoadmapFeedbackListResponse> getPreGuideRoadmapFeedbackList(final Long memberId) {
        return roadmapFeedbackRepository.findUnratedAllByRaterId(memberId)
                .stream()
                .map(RoadmapFeedbackListResponse::from)
                .toList();
    }

    @Transactional
    public void submitPreGuideRoadmapFeedbackRating(final Long memberId,
                                                    final Long feedbackId,
                                                    final RoadmapFeedbackRatingRequest request) {
        RoadmapCompletionFeedback feedback = roadmapFeedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new RuntimeException("Feedback Not Found"));

        roadmapFeedbackRatingRepository.save(RoadmapCompletionFeedbackRating.builder()
                .completionFeedback(feedback)
                .raterId(memberId)
                .expertise(request.expertiseScore())
                .help(request.helpScore())
                .recommend(request.recommendScore())
                .build());
    }

    public List<RoadmapFeedbackRatingListResponse> getPreGuideRoadmapFeedbackRatingList(final Long memberId) {
        return roadmapFeedbackRatingRepository.findAllStatsByAuthor(memberId);
    }

}
