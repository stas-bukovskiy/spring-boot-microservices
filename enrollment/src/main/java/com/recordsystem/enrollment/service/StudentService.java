package com.recordsystem.enrollment.service;

import com.recordsystem.enrollment.dto.StudentDto;

public interface StudentService {
    StudentDto getStudentById(Long studentId);
}
