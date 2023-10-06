package com.recordsystem.enrollment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Entity
@Getter
public class Student {
    @Id
    String id;

    @ManyToMany(mappedBy = "students")
    List<Course> courses;
}
