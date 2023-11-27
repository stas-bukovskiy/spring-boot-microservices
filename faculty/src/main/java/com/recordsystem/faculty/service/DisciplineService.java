package com.recordsystem.faculty.service;

import com.recordsystem.faculty.model.Discipline;

import java.util.List;

public interface DisciplineService {
    List<Discipline> getAllDisciplines();

    Discipline getDisciplineById(Long id);

    Discipline createDiscipline(String name, String description, Long facultyId);

    Discipline updateDiscipline(Long id, String name, String description, Long facultyId);

    void delete(Long id);
}
