package com.forink.forink.member.entity.dao;

import com.forink.forink.member.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
