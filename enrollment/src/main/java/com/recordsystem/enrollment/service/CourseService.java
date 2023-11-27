package com.recordsystem.enrollment.service;

import com.recordsystem.enrollment.dto.DisciplineDto;

import java.util.Optional;

public interface CourseService {
    Optional<DisciplineDto> getDisciplineById(Long discipleID);
}
