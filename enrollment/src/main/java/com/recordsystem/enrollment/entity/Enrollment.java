package com.recordsystem.enrollment.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table("enrollment")
public class Enrollment {

    @Id
    private Long id;

    @Column("course_id")
    private Long courseId;

    @Column("student_id")
    private Long studentId;

}
