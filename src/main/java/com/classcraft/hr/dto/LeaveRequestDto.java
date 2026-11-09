package com.classcraft.hr.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record LeaveRequestDto(
        @NotNull Long leaveTypeId,
        @NotNull LocalDate startDate,
        @NotNull LocalDate endDate,
        String reason
) {}
