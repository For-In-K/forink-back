package com.forink.forink.exam.entity.dao;

import com.forink.forink.exam.entity.Exam;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamRepository extends JpaRepository<Exam, Long> {
}
