package com.recordsystem.enrollment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table("course")
public class Course {
    @Id
    private Long id;
    private String name;
    private double credits;
    private int workload;
    private String description;
    private Faculty faculty;

    @Transient
    private List<StudentCourse> studentCourses;

    @Transient
    private List<Student> students;
}
