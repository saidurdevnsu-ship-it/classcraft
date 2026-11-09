package com.javaeduapp.questionbank;

import com.javaeduapp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "final_questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalQuestion extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "final_paper_id", nullable = false)
    private FinalPaper finalPaper;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(nullable = false)
    private int orderIndex;

    private Integer marks;

    @Column(columnDefinition = "TEXT")
    private String customText;
}
