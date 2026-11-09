package com.javaeduapp.questionbank.dto;

import jakarta.validation.constraints.NotBlank;

public record SubjectRequest(
        @NotBlank String name,
        String code,
        Long gradeId,
        String description
) {}
