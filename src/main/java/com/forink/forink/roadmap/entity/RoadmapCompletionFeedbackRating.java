package com.forink.forink.roadmap.entity;

import com.forink.forink.global.base.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
    private Integer expertise;

    @Column(nullable = false)
    private Integer help;

    @Column(nullable = false)
    private Integer recommend;

    @Builder
    private RoadmapCompletionFeedbackRating(final RoadmapCompletionFeedback completionFeedback,
                                            final Long raterId,
                                            final Integer expertise,
                                            final Integer help,
                                            final Integer recommend) {
        this.completionFeedback = completionFeedback;
        this.raterId = raterId;
        this.expertise = expertise;
        this.help = help;
        this.recommend = recommend;
    }
}
