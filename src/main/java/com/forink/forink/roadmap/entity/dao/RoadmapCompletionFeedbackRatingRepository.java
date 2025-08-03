package com.forink.forink.roadmap.entity.dao;

import com.forink.forink.roadmap.application.dto.response.RoadmapFeedbackRatingListResponse;
import com.forink.forink.roadmap.entity.RoadmapCompletionFeedbackRating;
import java.util.List;
import java.util.Optional;
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

    /**
     * 예비 가이드가 작성한 피드백 중, 아래 두 조건을 모두 만족하는지 확인
     * 1. 1개 이상의 평가가 달린 피드백이 10개 이상인가?
     * 2. 해당 피드백들에 달린 모든 평가의 전체 평균 점수가 3.0 이상인가?
     *
     * @param authorId 작성자 ID
     * @return 조건을 만족하면 authorId를, 만족하지 않으면 empty 반환
     */
    @Query("SELECT m.id " +
            "FROM RoadmapCompletionFeedbackRating r " +
            "JOIN r.completionFeedback f " +
            "JOIN f.roadmap.member m " +
            "WHERE m.id = :authorId " +
            "GROUP BY m.id " +
            "HAVING " +
            "    COUNT(DISTINCT f.id) >= 10 " +
            "    AND " +
            "    AVG((r.expertise + r.help + r.recommend) / 3.0) >= 3.0")
    Optional<Long> checkAuthorRatingStatus(@Param("authorId") Long authorId);
}
