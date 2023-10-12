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
@Table("student")
public class Student {
    @Id
    private Long id;
    private String name;

    @Transient
    private List<StudentCourse> studentCourses;
}
