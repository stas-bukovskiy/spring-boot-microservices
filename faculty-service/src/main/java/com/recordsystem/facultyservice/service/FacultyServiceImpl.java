package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.model.Faculty;
import com.recordsystem.facultyservice.repository.FacultyRepository;
import com.recordsystem.facultyservice.response.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FacultyServiceImpl implements FacultyService{

    private final FacultyRepository facultyRepository;
    private final JmsTemplate jmsTemplate;
    @Value("${activemq.faculty.queue.name}")
    private String queue;

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
        Email email = new Email("a@mail.com","Faculty"+ faculty +" created");
//        log.info(String.format("queue: %s, for %s", queue, email));
        jmsTemplate.convertAndSend(queue, email.toString());
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
