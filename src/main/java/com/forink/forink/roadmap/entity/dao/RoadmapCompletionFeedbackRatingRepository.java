package com.forink.forink.roadmap.entity.dao;

import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingListResponse;
import com.forink.forink.roadmap.entity.RoadmapCompletionFeedbackRating;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface RoadmapCompletionFeedbackRatingRepository extends
        JpaRepository<RoadmapCompletionFeedbackRating, Long> {

    @Query("SELECT new com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingListResponse(" +
            "    f.id, " +
            "    f.roadmap.title, " +
            "    COUNT(r), " +
            "    AVG((r.expertise + r.help + r.recommend) / 3.0), " +
            "    AVG(r.expertise), " +
            "    AVG(r.help), " +
            "    AVG(r.recommend) " +
            ") " +
            "FROM RoadmapCompletionFeedbackRating r " +
            "JOIN r.completionFeedback f " +
            "JOIN f.roadmap.member m " +
            "WHERE m.id = :authorId " +
            "GROUP BY f.id, f.roadmap.title")
    List<RoadmapFeedbackRatingListResponse> findAllStatsByAuthor(@Param("authorId") Long authorId);
}
