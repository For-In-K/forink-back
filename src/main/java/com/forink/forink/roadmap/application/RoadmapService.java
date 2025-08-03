package com.forink.forink.roadmap.application;

import static com.forink.forink.exam.entity.StatusType.COMPLETED;

import com.forink.forink.member.entity.Member;
import com.forink.forink.roadmap.application.dto.response.RoadmapListResponse;
import com.forink.forink.roadmap.entity.Roadmap;
import com.forink.forink.roadmap.entity.RoadmapType;
import com.forink.forink.roadmap.entity.dao.RoadmapRepository;
import java.util.Arrays;
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
}
