package com.forink.forink.roadmap.entity;

import com.forink.forink.global.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoadmapStep extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roadmap_id", nullable = false)
    private Roadmap roadmap;

    @Column(nullable = false)
    private Integer stepNumber;

    @Column(nullable = false)
    private String title;

    @Column
    private String description;

    @OneToMany(mappedBy = "roadmapStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoadmapStepContent> roadmapStepContents = new ArrayList<>();

    @OneToOne(mappedBy = "roadmapStep", cascade = CascadeType.ALL, orphanRemoval = true)
    private RoadmapStepFeedback stepFeedback;

    @Builder
    private RoadmapStep(final Roadmap roadmap, final Integer stepNumber, final String title, final String description) {
        this.roadmap = roadmap;
        this.stepNumber = stepNumber;
        this.title = title;
        this.description = description;
    }
}
