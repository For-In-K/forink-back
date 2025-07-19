package com.forink.forink.resume.entity;

import static com.forink.forink.exam.entity.StatusType.IN_PROGRESS;

import com.forink.forink.exam.entity.StatusType;
import com.forink.forink.member.entity.Member;
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
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Resume extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusType statusType;

    @Column
    private String answerName;

    @Column
    private String answerAge;

    @Column
    private String answerNationality;

    @Column
    private String answerLanguage;

    @Column
    private String answerExpertise;

    @Column
    private String answerLink;

    @Builder
    private Resume(final Member member) {
        this.member = member;
        this.statusType = IN_PROGRESS;
    }
}
