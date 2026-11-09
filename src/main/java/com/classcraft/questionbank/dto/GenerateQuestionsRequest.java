package com.classcraft.questionbank.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record GenerateQuestionsRequest(
        @NotNull Long subjectId,
        @NotNull Long knowledgeSourceId,
        @Min(1) @Max(50) int count,
        String questionType,
        String difficulty
) {}
