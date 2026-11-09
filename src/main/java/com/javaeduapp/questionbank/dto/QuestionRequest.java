package com.javaeduapp.questionbank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record QuestionRequest(
        @NotNull Long subjectId,
        @NotBlank String questionText,
        String questionType,
        String optionA,
        String optionB,
        String optionC,
        String optionD,
        String correctOption,
        String explanation,
        String difficulty
) {}
