package com.forink.forink.roadmap.entity.dao;

import com.forink.forink.roadmap.entity.RoadmapCompletionFeedback;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadmapCompletionFeedbackRepository extends JpaRepository<RoadmapCompletionFeedback, Long> {

    @Query("SELECT DISTINCT f "
            + "FROM RoadmapCompletionFeedback f "
            + "LEFT JOIN FETCH f.roadmap rm "
            + "LEFT JOIN FETCH rm.member "
            + "LEFT JOIN RoadmapCompletionFeedbackRating r "
            + "ON f.id = r.completionFeedback.id AND r.raterId = :raterId "
            + "WHERE r.id IS NULL AND rm.member.memberRoleType = 'ROLE_예비가이드'")
    List<RoadmapCompletionFeedback> findUnratedAllByRaterId(@Param("raterId") Long raterId);
}
