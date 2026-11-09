package com.classcraft.questionbank;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface QuestionRepository extends JpaRepository<Question, Long> {
    Page<Question> findBySubjectId(Long subjectId, Pageable pageable);
    Page<Question> findByStatus(Question.QuestionStatus status, Pageable pageable);
}
