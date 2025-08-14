package com.forink.forink.guide.entity;

import com.forink.forink.global.base.BaseEntity;
import com.forink.forink.member.entity.Member;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "guide_blockchain_account")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuideBlockchainAccount extends BaseEntity {

    private static final int ETHEREUM_ADDRESS_LENGTH = 42;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", unique = true, nullable = false)
    private Member member;

    @Column(name = "eth_address", length = ETHEREUM_ADDRESS_LENGTH, nullable = false)
    private String ethereumAddress;

    @Column(nullable = false)
    private String privateKey;

    @Builder
    private GuideBlockchainAccount(final Member member,
                                   final String ethereumAddress,
                                   final String privateKey) {
        this.member = member;
        this.ethereumAddress = ethereumAddress;
        this.privateKey = privateKey;
    }

}
