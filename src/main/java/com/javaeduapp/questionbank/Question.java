package com.javaeduapp.questionbank;

import com.javaeduapp.common.BaseEntity;
import com.javaeduapp.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "questions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Question extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @NotBlank
    @Column(nullable = false, columnDefinition = "TEXT")
    private String questionText;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private QuestionType questionType = QuestionType.MCQ;

    @Column(columnDefinition = "TEXT")
    private String optionA;
    @Column(columnDefinition = "TEXT")
    private String optionB;
    @Column(columnDefinition = "TEXT")
    private String optionC;
    @Column(columnDefinition = "TEXT")
    private String optionD;

    @Column(length = 1)
    private String correctOption; // A, B, C, D

    @Column(columnDefinition = "TEXT")
    private String explanation;

    @Column(length = 20)
    private String difficulty; // EASY, MEDIUM, HARD

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Column(nullable = false)
    @Builder.Default
    private boolean aiGenerated = false;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    @Builder.Default
    private QuestionStatus status = QuestionStatus.DRAFT;

    public enum QuestionType {
        MCQ, SHORT, ESSAY
    }

    public enum QuestionStatus {
        DRAFT, PENDING, APPROVED, REJECTED
    }
}
