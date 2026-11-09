package com.classcraft.questionbank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AcademicEventRepository extends JpaRepository<AcademicEvent, Long> {
    List<AcademicEvent> findBySubjectId(Long subjectId);
}
