package com.forink.forink.exam.entity.dao;

import com.forink.forink.exam.entity.Exam;
import com.forink.forink.exam.entity.ExamStep;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExamStepRepository extends JpaRepository<ExamStep, Long> {

    List<ExamStep> findAllByExamOrderByStepNumberAsc(final Exam exam);
}
