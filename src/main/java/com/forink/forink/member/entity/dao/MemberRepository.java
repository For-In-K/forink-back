package com.forink.forink.member.entity.dao;

import com.forink.forink.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByGoogleId(String googleId);
}
