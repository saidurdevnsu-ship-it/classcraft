package com.classcraft.questionbank;

import com.classcraft.common.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "knowledge_sources")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class KnowledgeSource extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @Column(length = 500)
    private String sourceUrl;
}
