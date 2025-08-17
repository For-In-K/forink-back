package com.forink.forink.roadmap.application;

import static com.forink.forink.global.error.ErrorCode.FAILED_TO_PROCESS_BLOCKCHAIN_TRANSACTION;
import static com.forink.forink.global.error.ErrorCode.GUIDE_BLOCKCHAIN_ACCOUNT_NOT_FOUND;
import static com.forink.forink.global.error.ErrorCode.ROADMAP_FEEDBACK_NOT_FOUND;

import com.forink.forink.global.error.BusinessException;
import com.forink.forink.guide.entity.GuideBlockchainAccount;
import com.forink.forink.guide.entity.dao.GuideBlockchainAccountRepository;
import com.forink.forink.guide.util.BlockchainKeyHelper;
import com.forink.forink.guide.util.GuideVerificationManager;
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
import java.math.BigInteger;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RoadmapFeedbackService {

    private final RoadmapCompletionFeedbackRepository roadmapFeedbackRepository;

    private final RoadmapCompletionFeedbackRatingRepository roadmapFeedbackRatingRepository;

    private final GuideBlockchainAccountRepository guideBlockchainAccountRepository;

    private final BlockchainKeyHelper blockchainKeyHelper;

    private final GuideVerificationManager guideVerificationManager;

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

        rateFeedbackOnBlockchain(memberId, feedbackId, request);
    }

    private void rateFeedbackOnBlockchain(final Long memberId,
                                          final Long feedbackId,
                                          final RoadmapFeedbackRatingRequest request) {
        GuideBlockchainAccount account = guideBlockchainAccountRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new BusinessException(GUIDE_BLOCKCHAIN_ACCOUNT_NOT_FOUND));
        try {
            String privateKey = blockchainKeyHelper.decrypt(account.getPrivateKey());
            guideVerificationManager.loadGuideVerification(privateKey)
                    .rateFeedback(
                            BigInteger.valueOf(feedbackId),
                            BigInteger.valueOf(request.expertiseScore()),
                            BigInteger.valueOf(request.helpScore()),
                            BigInteger.valueOf(request.recommendScore())
                    ).send();
        } catch (Exception e) {
            throw new BusinessException(FAILED_TO_PROCESS_BLOCKCHAIN_TRANSACTION);
        }
    }

    public List<RoadmapFeedbackRatingListResponse> getPreGuideRoadmapFeedbackRatingList(final Long memberId) {
        return roadmapFeedbackRatingRepository.findAllStatsByAuthor(memberId);
    }

    public RoadmapFeedbackRatingStatusResponse getPreGuideRoadmapFeedbackRatingStatus(final Long memberId) {
        GuideBlockchainAccount account = guideBlockchainAccountRepository.findByMember_Id(memberId)
                .orElseThrow(() -> new BusinessException(GUIDE_BLOCKCHAIN_ACCOUNT_NOT_FOUND));

        try {
            String privateKey = blockchainKeyHelper.decrypt(account.getPrivateKey());
            String status = guideVerificationManager.loadGuideVerification(privateKey)
                    .getGuideStatus(account.getEthereumAddress())
                    .send();
            return new RoadmapFeedbackRatingStatusResponse(RoadmapFeedbackRatingStatusType.valueOf(status));
        } catch (Exception e) {
            throw new BusinessException(FAILED_TO_PROCESS_BLOCKCHAIN_TRANSACTION);
        }
    }

}
