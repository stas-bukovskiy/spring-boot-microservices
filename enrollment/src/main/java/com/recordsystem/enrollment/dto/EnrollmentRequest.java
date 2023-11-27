package com.recordsystem.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @JsonProperty("student_id")
        @NotNull(message = "student_id is mandatory")
        Long studentId,
        @JsonProperty("discipline_id")
        @NotNull(message = "discipline_id is mandatory")
        Long disciplineId) {
}
