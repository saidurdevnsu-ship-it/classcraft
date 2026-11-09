package com.classcraft.questionbank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuestionBlueprintRepository extends JpaRepository<QuestionBlueprint, Long> {
    List<QuestionBlueprint> findBySubjectId(Long subjectId);
}
