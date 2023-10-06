package com.recordsystem.enrollment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table("student_course")
public class StudentCourse {
    @Id
    private Long id;
    private Long courseId;
    private Long studentId;
}
