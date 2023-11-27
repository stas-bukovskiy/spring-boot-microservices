package com.recordsystem.faculty.service;

import com.recordsystem.faculty.model.Faculty;

import java.util.List;

public interface FacultyService {

    List<Faculty> getAllFaculties();
    Faculty getFacultyById(Long id);
    Faculty createFaculty(String name, String description);
    Faculty updateFaculty(Long id, String name, String description);
    void delete(Long id);
}
