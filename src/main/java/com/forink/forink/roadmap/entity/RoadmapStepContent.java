package com.forink.forink.roadmap.entity;

import static java.lang.Boolean.TRUE;

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
public class RoadmapStepContent extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "roadmap_step_id", nullable = false)
    private RoadmapStep roadmapStep;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false)
    private Boolean isChecked;

    @Builder
    private RoadmapStepContent(final RoadmapStep roadmapStep, final String content) {
        this.roadmapStep = roadmapStep;
        this.content = content;
        this.isChecked = false;
    }

    public void updateIsChecked() {
        this.isChecked = (this.isChecked == null) ? TRUE : !this.isChecked;
    }

    public void setRoadmapStep(final RoadmapStep step){
        this.roadmapStep = step;
    }
}
