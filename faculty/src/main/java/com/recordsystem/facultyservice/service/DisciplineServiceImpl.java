package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.model.Discipline;
import com.recordsystem.facultyservice.repository.DisciplineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RequiredArgsConstructor
@Service
public class DisciplineServiceImpl implements DisciplineService {

    private final DisciplineRepository repository;

    @Override
    public List<Discipline> getAllDisciplines() {
        return repository.findAll();
    }

    @Override
    public Discipline getDisciplineById(Long id) {
        return repository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "discipline is not found"));
    }

    @Override
    public Discipline save(Discipline discipline) {
        return repository.save(discipline);
    }

    @Override
    public Discipline update(Long id, Discipline discipline) {
        discipline.setId(id);
        return repository.save(discipline);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
