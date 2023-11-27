package com.recordsystem.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DisciplineDto {
    private Long id;
    private String name;
    private String description;
    private FacultyDto faculty;
}
