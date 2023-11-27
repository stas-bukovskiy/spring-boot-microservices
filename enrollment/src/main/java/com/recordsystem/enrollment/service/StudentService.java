package com.recordsystem.enrollment.service;

import com.recordsystem.enrollment.dto.StudentDto;

import java.util.Optional;

public interface StudentService {
    Optional<StudentDto> getStudentById(Long studentId);
}
