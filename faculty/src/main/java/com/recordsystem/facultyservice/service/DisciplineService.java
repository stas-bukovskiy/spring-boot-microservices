package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.model.Discipline;
import com.recordsystem.facultyservice.model.Faculty;

import java.util.List;

public interface DisciplineService {
    List<Discipline> getAllDisciplines();

    Discipline getDisciplineById(Long id);

    Discipline save(Discipline discipline);

    Discipline update(Long id, Discipline discipline);

    void delete(Long id);
}
