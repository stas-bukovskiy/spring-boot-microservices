package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.model.Faculty;
import com.recordsystem.facultyservice.repository.FacultyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FacultyServiceImpl implements FacultyService{

    private final FacultyRepository facultyRepository;
    private final AmqpTemplate amqpTemplate;

    @Value("${rabbitmq.faculty.queue.name}")
    private String queue;

    @Value("${rabbitmq.faculty.routing.key}")
    private String routingKey;

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
        amqpTemplate.convertAndSend(queue, routingKey, "Faculty " + faculty.getName() + " has been created");
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
