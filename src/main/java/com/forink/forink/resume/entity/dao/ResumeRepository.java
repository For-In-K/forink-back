package com.forink.forink.resume.entity.dao;

import com.forink.forink.member.entity.Member;
import com.forink.forink.member.entity.MemberRoleType;
import com.forink.forink.resume.entity.Resume;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByMember_Id(Long memberId);

    @EntityGraph(attributePaths = {"member"})
    List<Resume> findAllByMember_MemberRoleType(MemberRoleType memberMemberRoleType);

    boolean existsByMember(final Member member);
}
