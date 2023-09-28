package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.model.Faculty;
import com.recordsystem.facultyservice.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService{

    private final FacultyRepository facultyRepository;
    @Override
    public List<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    @Override
    public Faculty getFacultyById(Long id) {
        return facultyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));
    }

    @Override
    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        Faculty fac = facultyRepository.findById(faculty.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));
        fac.setId(faculty.getId());
        fac.setName(faculty.getName());
        fac.setDescription(faculty.getDescription());

        return facultyRepository.save(fac);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.deleteById(id);
    }
}
