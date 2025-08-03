package com.forink.forink.roadmap.application;

import static com.forink.forink.exam.entity.StatusType.COMPLETED;

import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.application.dto.request.RoadmapEntireFeedbackRequest;
import com.forink.forink.roadmap.application.dto.request.RoadmapTypeFeedbackRequest;
import com.forink.forink.roadmap.application.dto.response.RoadmapContentResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapListResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapTypeDetailResponse;
import com.forink.forink.roadmap.application.dto.response.RoadmapTypeListResponse;
import com.forink.forink.roadmap.entity.Roadmap;
import com.forink.forink.roadmap.entity.RoadmapCompletionFeedback;
import com.forink.forink.roadmap.entity.RoadmapStep;
import com.forink.forink.roadmap.entity.RoadmapStepContent;
import com.forink.forink.roadmap.entity.RoadmapStepFeedback;
import com.forink.forink.roadmap.entity.RoadmapType;
import com.forink.forink.roadmap.entity.dao.RoadmapCompletionFeedbackRepository;
import com.forink.forink.roadmap.entity.dao.RoadmapRepository;
import com.forink.forink.roadmap.entity.dao.RoadmapStepContentRepository;
import com.forink.forink.roadmap.entity.dao.RoadmapStepFeedbackRepository;
import com.forink.forink.roadmap.entity.dao.RoadmapStepRepository;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class RoadmapService {

    private final RoadmapRepository roadmapRepository;
    private final RoadmapStepContentRepository roadmapStepContentRepository;
    private final RoadmapStepRepository roadmapStepRepository;
    private final RoadmapStepFeedbackRepository roadmapStepFeedbackRepository;
    private final RoadmapCompletionFeedbackRepository roadmapCompletionFeedbackRepository;

    public List<RoadmapListResponse> getAllRoadmapList(final Member member) {
        final List<Roadmap> roadmaps = roadmapRepository.findAllByMember(member);

        Map<RoadmapType, Long> totalByType = roadmaps.stream()
                .collect(Collectors.groupingBy(Roadmap::getRoadmapType, Collectors.counting()));

        Map<RoadmapType, Long> completedByType = roadmaps.stream()
                .filter(r -> r.getStatusType() == COMPLETED)
                .collect(Collectors.groupingBy(Roadmap::getRoadmapType, Collectors.counting()));

        return Arrays.stream(RoadmapType.values())
                .map(type -> {
                    long total = totalByType.getOrDefault(type, 0L);
                    long completed = completedByType.getOrDefault(type, 0L);
                    int percent = total == 0 ? 0 : (int) Math.round(completed * 100.0 / total);
                    return new RoadmapListResponse(type, percent);
                })
                .toList();
    }

    public List<RoadmapTypeListResponse> getRoadmapTypeList(final RoadmapType roadmapType, final Member member) {
        final List<Roadmap> roadmaps = roadmapRepository.findAllByMemberAndRoadmapTypeOrderByOrderAsc(member,
                roadmapType);

        return roadmaps.stream()
                .map(r -> new RoadmapTypeListResponse(r.getId(), r.getTitle(), r.getStatusType()))
                .toList();
    }

    public List<RoadmapTypeDetailResponse> getRoadmapTypeDetails(final Long roadmapId, final Member member) {
        final Roadmap roadmap = roadmapRepository.findById(roadmapId).orElseThrow();
        if (!roadmap.isMine(member)) {
            throw new RuntimeException();
        }

        return roadmap.getSteps().stream()
                .sorted(Comparator.comparingInt(RoadmapStep::getStepNumber))
                .map(step -> {
                    final List<RoadmapContentResponse> contents = step.getRoadmapStepContents().stream()
                            .map(content -> new RoadmapContentResponse(content.getId(), content.getContent(),
                                    content.getIsChecked()))
                            .toList();

                    return new RoadmapTypeDetailResponse(step.getStepNumber(), step.getTitle(), step.getDescription(),
                            contents);
                })
                .toList();
    }

    public void updateRoadmapIsChecked(final Long roadmapStepContentId, final Member member) {
        final RoadmapStepContent roadmapStepContent = roadmapStepContentRepository.findById(roadmapStepContentId)
                .orElseThrow();
        if (!roadmapStepContent.getRoadmapStep().getRoadmap().isMine(member)) {
            throw new RuntimeException();
        }

        roadmapStepContent.updateIsChecked();
    }

    public void createRoadmapTypeFeedback(final Long roadmapStepId, final RoadmapTypeFeedbackRequest request,
                                          final Member member) {
        final RoadmapStep roadmapStep = roadmapStepRepository.findById(roadmapStepId).orElseThrow();
        if (!roadmapStep.getRoadmap().isMine(member)) {
            throw new RuntimeException();
        }

        roadmapStepFeedbackRepository.save(RoadmapStepFeedback.builder()
                .roadmapStep(roadmapStep)
                .type(request.roadmapAnswerType())
                .build());
    }

    public void createRoadmapEntireFeedback(final Long roadmapId, final RoadmapEntireFeedbackRequest request,
                                            final Member member) {
        final Roadmap roadmap = roadmapRepository.findById(roadmapId).orElseThrow();
        if (roadmap.isMine(member)) {
            throw new RuntimeException();
        }

        roadmapCompletionFeedbackRepository.save(RoadmapCompletionFeedback.builder()
                .roadmap(roadmap)
                .content(request.content())
                .build());
    }
}
