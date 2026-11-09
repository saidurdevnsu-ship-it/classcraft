package com.javaeduapp.questionbank.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record AcademicEventRequest(
        @NotBlank String title,
        String description,
        @NotNull LocalDate eventDate,
        String eventType,
        Long subjectId
) {}
