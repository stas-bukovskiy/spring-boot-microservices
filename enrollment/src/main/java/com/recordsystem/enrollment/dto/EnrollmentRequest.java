package com.recordsystem.enrollment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record EnrollmentRequest(
        @JsonProperty("student_id")
        @NotNull(message = "student_id is mandatory")
        Long studentId,
        @JsonProperty("course_id")
        @NotNull(message = "course_id is mandatory")
        Long courseId) {
}
