package com.javaeduapp.hr.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;

public record LeaveTypeRequest(
        @NotBlank String name,
        @Min(0) int maxDaysPerYear,
        String description
) {}
