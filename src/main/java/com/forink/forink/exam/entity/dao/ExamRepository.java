package com.forink.forink.exam.entity.dao;

import com.forink.forink.exam.entity.Exam;
import com.forink.forink.member.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {

    Optional<Exam> findByMember(final Member member);
    boolean existsByMember(final Member member);
}
