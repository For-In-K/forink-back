package com.forink.forink.roadmap.entity;

import com.forink.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadmapCompletionFeedbackRating extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roadmap_completion_feedback_id", nullable = false)
    private RoadmapCompletionFeedback completionFeedback;

    @Column(nullable = false)
    private Long raterId;

    @Column(nullable = false)
    private Double expertise;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoadmapAnswerType relevance;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoadmapAnswerType recommend;

    @Builder
    private RoadmapCompletionFeedbackRating(final RoadmapCompletionFeedback completionFeedback, final Long raterId,
                                           final Double expertise,
                                           final RoadmapAnswerType relevance, final RoadmapAnswerType recommend) {
        this.completionFeedback = completionFeedback;
        this.raterId = raterId;
        this.expertise = expertise;
        this.relevance = relevance;
        this.recommend = recommend;
    }
}
