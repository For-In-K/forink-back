package com.forink.forink.member.entity;

import static com.forink.forink.member.entity.MemberRoleType.ROLE_예비가이드;
import static com.forink.forink.member.entity.MemberRoleType.ROLE_회원;

import com.forink.forink.roadmap.entity.Roadmap;
import com.forink.forink.global.base.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String googleId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Integer point;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberRoleType memberRoleType;

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Roadmap> roadmaps = new ArrayList<>();

    @Builder
    private Member(final String name, final String googleId, final String email, final MemberRoleType memberRoleType,
                   final List<Roadmap> roadmaps) {
        this.name = name;
        this.googleId = googleId;
        this.email = email;
        this.point = 0;
        this.memberRoleType = memberRoleType;
        this.roadmaps = roadmaps;
    }

    public void qualifyAsPreGuide() {
        if (this.memberRoleType != ROLE_회원) {
            throw new IllegalStateException("오직 일반 회원만 예비 가이드 자격을 얻을 수 있습니다.");
        }
        this.memberRoleType = ROLE_예비가이드;
    }
}
