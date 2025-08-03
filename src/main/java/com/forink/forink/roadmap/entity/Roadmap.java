package com.forink.forink.roadmap.entity;

import static com.forink.forink.exam.entity.StatusType.IN_PROGRESS;

import com.forink.forink.exam.entity.StatusType;
import com.forink.forink.global.base.BaseEntity;
import com.forink.forink.member.entity.Member;
import jakarta.persistence.CascadeType;
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
public class Roadmap extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer order;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType statusType;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RoadmapType roadmapType;

    @OneToMany(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RoadmapStep> steps = new ArrayList<>();

    @OneToOne(mappedBy = "roadmap", cascade = CascadeType.ALL, orphanRemoval = true)
    private RoadmapCompletionFeedback roadmapCompletionFeedback;

    @Builder
    private Roadmap(final Member member, final String title, final Integer order, final RoadmapType roadmapType) {
        this.member = member;
        this.title = title;
        this.order = order;
        this.roadmapType = roadmapType;
        this.statusType = IN_PROGRESS;
    }

    public boolean isMine(final Member member) {
        return this.member.equals(member);
    }
}
