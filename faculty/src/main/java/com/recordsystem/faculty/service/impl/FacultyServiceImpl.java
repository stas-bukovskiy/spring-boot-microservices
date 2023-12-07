package com.recordsystem.faculty.service.impl;

import com.recordsystem.faculty.model.Faculty;
import com.recordsystem.faculty.repository.FacultyRepository;
import com.recordsystem.faculty.response.Email;
import com.recordsystem.faculty.service.FacultyService;
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
public class FacultyServiceImpl implements FacultyService {

    private final FacultyRepository facultyRepository;
    private final JmsTemplate jmsTemplate;
    @Value("${spring.activemq.topic-name}")
    private String queue;
    @Value("${activemq.faculty.delete}")
    private String deleteQueue;
    @Value("${activemq.faculty.get}")
    private String getQueueFiltering;

    @Override
    public List<Faculty> getAllFaculties() {
        jmsTemplate.convertAndSend(getQueueFiltering, "getAllFaculties - not - important", message -> {
            message.setStringProperty("filterCriteria", "not-important");
            return message;
        });

        return facultyRepository.findAll();
    }

    @Override
    public Faculty getFacultyById(Long id) {
        jmsTemplate.convertAndSend(getQueueFiltering, "getOneFaculties - important", message -> {
            message.setStringProperty("filterCriteria", "important");
            return message;
        });

        return facultyRepository.findById(id).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));
    }

    @Override
    public Faculty createFaculty(String name, String description) {
        Email email = new Email("a@mail.com","Faculty " + name + " created");

        jmsTemplate.convertAndSend(queue, email.toString());

        if (facultyRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "discipline with such name is already exist");
        }

        return facultyRepository.save(facultyRepository.save(Faculty.builder()
                .name(name)
                .description(description)
                .build()));
    }

    @Override
    public Faculty updateFaculty(Long id, String name, String description) {
        Faculty fac = facultyRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Faculty not found"));
        fac.setName(name);
        fac.setDescription(description);

        return facultyRepository.save(fac);
    }

    @Override
    public void delete(Long id) {
        facultyRepository.deleteById(id);
        jmsTemplate.convertAndSend(deleteQueue, String.format("Faculty with id: %d deleted", id));
    }
}
