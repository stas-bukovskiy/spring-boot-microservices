package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getAllFaculties();
    Faculty getFacultyById(Long id);
    Faculty save(Faculty faculty);
    Faculty update(Long id, Faculty faculty);
    void delete(Long id);
}
