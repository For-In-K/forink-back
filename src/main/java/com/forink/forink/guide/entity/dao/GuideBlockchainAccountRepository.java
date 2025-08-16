package com.forink.forink.guide.entity.dao;

import com.forink.forink.guide.entity.GuideBlockchainAccount;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GuideBlockchainAccountRepository extends JpaRepository<GuideBlockchainAccount, Long> {

    Optional<GuideBlockchainAccount> findByMember_Id(Long memberId);
}
