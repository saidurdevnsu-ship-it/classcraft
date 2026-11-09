package com.classcraft.questionbank;

import com.classcraft.common.BaseEntity;
import com.classcraft.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "final_papers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FinalPaper extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "blueprint_id")
    private QuestionBlueprint blueprint;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    private LocalDate examDate;

    @Column(length = 10)
    private String academicYear;

    @Column(length = 20)
    @Builder.Default
    private String status = "DRAFT";

    /** Confidential — only EXAM_CONTROLLER and superusers may access. */
    @OneToMany(mappedBy = "finalPaper", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<FinalQuestion> finalQuestions = new ArrayList<>();
}
