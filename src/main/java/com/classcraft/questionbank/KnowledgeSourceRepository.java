package com.classcraft.questionbank;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface KnowledgeSourceRepository extends JpaRepository<KnowledgeSource, Long> {
    List<KnowledgeSource> findBySubjectId(Long subjectId);
}
