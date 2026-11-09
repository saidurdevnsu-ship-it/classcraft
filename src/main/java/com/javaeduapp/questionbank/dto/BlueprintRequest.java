package com.javaeduapp.questionbank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record BlueprintRequest(
        @NotBlank String title,
        @NotNull Long subjectId,
        String academicYear,
        List<BlueprintItemRequest> items
) {
    public record BlueprintItemRequest(
            String topic,
            String questionType,
            int questionCount,
            String difficulty,
            Integer marks
    ) {}
}
