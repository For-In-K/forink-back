package com.forink.forink.roadmap.entity.dao;

import com.forink.forink.roadmap.entity.RoadmapCompletionFeedbackRating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoadmapCompletionFeedbackRatingRepository extends
        JpaRepository<RoadmapCompletionFeedbackRating, Long> {
}
