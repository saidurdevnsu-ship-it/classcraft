package com.classcraft.questionbank;

import com.classcraft.common.BaseEntity;
import com.classcraft.user.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "subject_assignments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectAssignment extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private User teacher;

    @Column(length = 10)
    private String academicYear;

    @Builder.Default
    private boolean isActive = true;
}
