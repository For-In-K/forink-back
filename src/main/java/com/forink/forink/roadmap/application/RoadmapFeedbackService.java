package com.forink.forink.roadmap.application;

import com.forink.forink.global.error.BusinessException;
import static com.forink.forink.global.error.ErrorCode.ROADMAP_FEEDBACK_NOT_FOUND;
import static com.forink.forink.roadmap.entity.RoadmapFeedbackRatingStatusType.ALMOST;
import static com.forink.forink.roadmap.entity.RoadmapFeedbackRatingStatusType.IN_PROGRESS;

import com.forink.forink.roadmap.application.dto.request.RoadmapFeedbackRatingRequest;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackListResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingListResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingStatusResponse;
import com.forink.forink.roadmap.entity.RoadmapCompletionFeedback;
import com.forink.forink.roadmap.entity.RoadmapCompletionFeedbackRating;
import com.forink.forink.roadmap.entity.RoadmapFeedbackRatingStatusType;
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
                .orElseThrow(() -> new BusinessException(ROADMAP_FEEDBACK_NOT_FOUND));

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

    public RoadmapFeedbackRatingStatusResponse getPreGuideRoadmapFeedbackRatingStatus(final Long memberId) {
        boolean isAlmost = roadmapFeedbackRatingRepository.checkAuthorRatingStatus(memberId).isPresent();

        RoadmapFeedbackRatingStatusType status = IN_PROGRESS;
        if (isAlmost) {
            status = ALMOST;
        }

        return new RoadmapFeedbackRatingStatusResponse(status);
    }

}
