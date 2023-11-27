package com.recordsystem.faculty.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record FacultyRequest(
        @NotNull(message = "name is mandatory")
        @NotBlank(message = "name is mandatory")
        String name,
        @NotNull(message = "description is mandatory")
        @NotBlank(message = "description is mandatory")
        String description
) {
}
