package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.model.Faculty;
import com.recordsystem.facultyservice.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
        return facultyRepository.getReferenceById(id);
    }

    @Override
    public Faculty save(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    @Override
    public Faculty update(Long id, Faculty faculty) {
        Faculty fac = facultyRepository.getReferenceById(faculty.getId());
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
