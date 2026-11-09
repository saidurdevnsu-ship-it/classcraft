package com.classcraft.hr.dto;

import jakarta.validation.constraints.NotBlank;

public record DepartmentRequest(@NotBlank String name, String description) {}
