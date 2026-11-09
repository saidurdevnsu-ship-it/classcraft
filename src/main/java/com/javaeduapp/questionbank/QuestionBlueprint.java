package com.javaeduapp.questionbank;

import com.javaeduapp.common.BaseEntity;
import com.javaeduapp.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "question_blueprints")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class QuestionBlueprint extends BaseEntity {

    @NotBlank
    @Column(nullable = false, length = 255)
    private String title;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    private User createdBy;

    @Column(length = 10)
    private String academicYear;

    @Column(length = 20)
    @Builder.Default
    private String status = "DRAFT";

    @OneToMany(mappedBy = "blueprint", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<BlueprintItem> items = new ArrayList<>();
}
