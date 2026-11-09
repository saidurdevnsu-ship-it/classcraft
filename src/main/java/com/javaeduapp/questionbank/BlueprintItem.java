package com.javaeduapp.questionbank;

import com.javaeduapp.common.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "blueprint_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BlueprintItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "blueprint_id", nullable = false)
    private QuestionBlueprint blueprint;

    @Column(length = 100)
    private String topic;

    @Column(length = 20)
    private String questionType;

    @Column(nullable = false)
    @Builder.Default
    private int questionCount = 1;

    @Column(length = 20)
    private String difficulty;

    private Integer marks;
}
