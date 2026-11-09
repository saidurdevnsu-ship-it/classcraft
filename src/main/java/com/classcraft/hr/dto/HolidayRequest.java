package com.classcraft.hr.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record HolidayRequest(
        @NotBlank String name,
        @NotNull LocalDate holidayDate,
        String holidayType,
        String description
) {}
