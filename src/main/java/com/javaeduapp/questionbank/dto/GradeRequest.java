package com.javaeduapp.questionbank.dto;

import jakarta.validation.constraints.NotBlank;

public record GradeRequest(@NotBlank String name, String description) {}
