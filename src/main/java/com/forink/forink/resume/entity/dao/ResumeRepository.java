package com.forink.forink.resume.entity.dao;

import com.forink.forink.member.entity.Member;
import com.forink.forink.resume.entity.Resume;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResumeRepository extends JpaRepository<Resume, Long> {

    Optional<Resume> findByMember(Member member);
}
