package com.javaeduapp.hr.dto;

import jakarta.validation.constraints.NotBlank;

public record DesignationRequest(@NotBlank String title, Long departmentId, String description) {}
