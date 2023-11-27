package com.recordsystem.faculty.service;

import com.google.common.collect.Lists;
import com.recordsystem.faculty.model.Discipline;
import com.recordsystem.faculty.model.Faculty;
import com.recordsystem.faculty.repository.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Service
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository repository;
    private final FacultyService facultyService;

    @Override
    public List<Discipline> getAllDisciplines() {
        return Lists.newArrayList(repository.findAll());
    }

    @Override
    public Discipline getDisciplineById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "discipline is not found"));
    }

    @Override
    @Transactional
    public Discipline createDiscipline(String name, String description, Long facultyId) {
        boolean isExist = repository.existsByName(name);
        if (isExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "discipline with such name is already exist");
        }

        Faculty faculty = facultyService.getFacultyById(facultyId);
        return repository.save(Discipline.builder()
                .name(name)
                .description(description)
                .faculty(faculty)
                .build());
    }

    @Override
    @Transactional
    public Discipline updateDiscipline(Long id, String name, String description, Long facultyId) {
        boolean isExist = repository.existsById(id);
        if (!isExist) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "discipline is not found");
        }

        isExist = repository.existsByNameAndIdIsNot(name, id);
        if (isExist) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "discipline with such name is already exist");
        }

        Faculty faculty = facultyService.getFacultyById(facultyId);
        return repository.save(Discipline.builder()
                .name(name)
                .description(description)
                .faculty(faculty)
                .build());
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
