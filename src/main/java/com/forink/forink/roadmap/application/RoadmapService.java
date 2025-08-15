package com.forink.forink.roadmap.application;

import static com.forink.forink.exam.entity.StatusType.COMPLETED;
import static com.forink.forink.global.error.ErrorCode.EXAM_NOT_FOUND;
import static com.forink.forink.global.error.ErrorCode.ROADMAP_ACCESS_DENIED;
import static com.forink.forink.global.error.ErrorCode.ROADMAP_NOT_FOUND;
import static com.forink.forink.global.error.ErrorCode.ROADMAP_STEP_CONTENT_NOT_FOUND;
import static com.forink.forink.global.error.ErrorCode.ROADMAP_STEP_NOT_FOUND;

import com.forink.forink.exam.entity.Exam;
import com.forink.forink.exam.entity.ExamStep;
import com.forink.forink.exam.entity.dao.ExamRepository;
import com.forink.forink.exam.entity.dao.ExamStepRepository;
import com.forink.forink.global.ai.AiClient;
import com.forink.forink.global.error.BusinessException;
import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.application.dto.request.AiRoadmapGenerateRequest;
import com.forink.forink.roadmap.application.dto.request.RoadmapEntireFeedbackRequest;
import com.forink.forink.roadmap.application.dto.request.RoadmapTypeFeedbackRequest;
import com.forink.forink.roadmap.application.dto.response.AiRoadmapGenerateResponse;
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

    private final ExamRepository examRepository;
    private final ExamStepRepository examStepRepository;

    private final AiClient aiClient;

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
        final List<Roadmap> roadmaps = roadmapRepository.findAllByMemberAndRoadmapTypeOrderByItemOrderAsc(member,
                roadmapType);

        return roadmaps.stream()
                .map(r -> new RoadmapTypeListResponse(r.getId(), r.getTitle(), r.getStatusType()))
                .toList();
    }

    public List<RoadmapTypeDetailResponse> getRoadmapTypeDetails(final Long roadmapId, final Member member) {
        final Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new BusinessException(ROADMAP_NOT_FOUND));
        if (roadmap.isNotMine(member)) {
            throw new BusinessException(ROADMAP_ACCESS_DENIED);
        }
        
        return getRoadmapTypeInfos(roadmap);
    }

    public void updateRoadmapIsChecked(final Long roadmapStepContentId, final Member member) {
        final RoadmapStepContent roadmapStepContent = roadmapStepContentRepository.findById(roadmapStepContentId)
                .orElseThrow(() -> new BusinessException(ROADMAP_STEP_CONTENT_NOT_FOUND));
        if (roadmapStepContent.getRoadmapStep().getRoadmap().isNotMine(member)) {
            throw new BusinessException(ROADMAP_ACCESS_DENIED);
        }

        roadmapStepContent.updateIsChecked();
    }

    public void createRoadmapTypeFeedback(final Long roadmapStepId, final RoadmapTypeFeedbackRequest request,
                                          final Member member) {
        final RoadmapStep roadmapStep = roadmapStepRepository.findById(roadmapStepId)
                .orElseThrow(() -> new BusinessException(ROADMAP_STEP_NOT_FOUND));
        if (roadmapStep.getRoadmap().isNotMine(member)) {
            throw new BusinessException(ROADMAP_ACCESS_DENIED);
        }

        roadmapStepFeedbackRepository.save(RoadmapStepFeedback.builder()
                .roadmapStep(roadmapStep)
                .type(request.roadmapAnswerType())
                .build());
    }

    public void createRoadmapEntireFeedback(final Long roadmapId, final RoadmapEntireFeedbackRequest request,
                                            final Member member) {
        final Roadmap roadmap = roadmapRepository.findById(roadmapId)
                .orElseThrow(() -> new BusinessException(ROADMAP_NOT_FOUND));
        if (roadmap.isNotMine(member)) {
            throw new BusinessException(ROADMAP_ACCESS_DENIED);
        }

        roadmapCompletionFeedbackRepository.save(RoadmapCompletionFeedback.builder()
                .roadmap(roadmap)
                .content(request.content())
                .build());

        roadmap.updateStatusType();
    }

    public void createRoadmaps(final Member member) {
        final Exam exam = examRepository.findByMember(member)
                .orElseThrow(() -> new BusinessException(EXAM_NOT_FOUND));
        final List<ExamStep> examSteps = examStepRepository.findAllByExamOrderByStepNumberAsc(exam);

        final AiRoadmapGenerateRequest aiRequest = createAIRequest(member, exam, examSteps);
        final AiRoadmapGenerateResponse[] aiResponses = aiClient.generateRoadmaps(aiRequest);
        saveRoadmaps(aiResponses, member);
    }

    private AiRoadmapGenerateRequest createAIRequest(final Member member, final Exam exam,
                                                     final List<ExamStep> examSteps) {
        return new AiRoadmapGenerateRequest(
                member.getId(),
                member.getMemberRoleType().name(),
                exam.getId(),
                examSteps.stream()
                        .map(s -> new AiRoadmapGenerateRequest.Answer(s.getStepNumber(), s.getAnswer()))
                        .toList()
        );
    }

    private List<RoadmapTypeDetailResponse> getRoadmapTypeInfos(final Roadmap roadmap) {
        final List<RoadmapStep> steps = roadmap.getSteps().stream()
                .sorted(Comparator.comparingInt(RoadmapStep::getStepNumber))
                .toList();

        final List<RoadmapStepContent> allContents = roadmapStepContentRepository.findAllByRoadmapStepIn(steps);

        final Map<Long, List<RoadmapStepContent>> contentsByStepId = allContents.stream()
                .collect(Collectors.groupingBy(c -> c.getRoadmapStep().getId()));

        return steps.stream()
                .map(step -> {
                    List<RoadmapTypeDetailResponse.RoadmapContent> contents = contentsByStepId
                            .getOrDefault(step.getId(), List.of())
                            .stream()
                            .map(content -> new RoadmapTypeDetailResponse.RoadmapContent(
                                    content.getId(),
                                    content.getContent(),
                                    content.getIsChecked()
                            )).toList();

                    return new RoadmapTypeDetailResponse(
                            step.getStepNumber(),
                            step.getTitle(),
                            step.getDescription(),
                            contents
                    );
                }).toList();
    }

    private void saveRoadmaps(final AiRoadmapGenerateResponse[] aiResponses, final Member member) {
        if (aiResponses == null || aiResponses.length == 0) return;
        final List<Roadmap> roadmaps = Arrays.stream(aiResponses)
                .map(ai -> {
                    final RoadmapType type = RoadmapType.valueOf(ai.type());
                    final Roadmap roadmap = Roadmap.builder()
                            .member(member)
                            .title(ai.title())
                            .itemOrder(ai.order())
                            .roadmapType(type)
                            .build();

                    ai.steps().forEach(aiStep -> {
                        final RoadmapStep step = RoadmapStep.builder()
                                .roadmap(roadmap)
                                .stepNumber(aiStep.stepNumber())
                                .title(aiStep.stepTitle())
                                .description(aiStep.stepDescription())
                                .build();
                        roadmap.addStep(step);

                        aiStep.contents().forEach(aiContent -> {
                            final RoadmapStepContent content = RoadmapStepContent.builder()
                                    .roadmapStep(step)
                                    .content(aiContent.stepContent())
                                    .build();
                            step.addContent(content);
                        });
                    });
                    return roadmap;
                }).toList();

        roadmapRepository.saveAll(roadmaps);
    }

}
