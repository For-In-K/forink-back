package com.forink.forink.roadmap.entity.dao;

import com.forink.forink.roadmap.entity.RoadmapStep;
import com.forink.forink.roadmap.entity.RoadmapStepContent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapStepContentRepository extends JpaRepository<RoadmapStepContent, Long> {

    List<RoadmapStepContent> findAllByRoadmapStepIn(final List<RoadmapStep> steps);

}
