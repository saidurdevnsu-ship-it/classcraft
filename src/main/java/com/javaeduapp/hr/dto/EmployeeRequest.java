package com.javaeduapp.hr.dto;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record EmployeeRequest(
        @NotNull Long userId,
        String employeeId,
        Long departmentId,
        Long designationId,
        LocalDate joiningDate,
        String employmentType,
        String phone
) {}
