package com.recordsystem.faculty.web.http;

import com.recordsystem.faculty.dto.FacultyRequest;
import com.recordsystem.faculty.model.Faculty;
import com.recordsystem.faculty.service.FacultyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/faculty")
@Slf4j
public class FacultyController {

    private final FacultyService facultyService;
    int i = 0;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Faculty>> getAllFaculties() {
        log.info("Request number {}", ++i);
        return ResponseEntity.ok(facultyService.getAllFaculties());
    }

    @SneakyThrows
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.TEXT_HTML_VALUE})
    public ResponseEntity<?> getFacultyById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(facultyService.getFacultyById(id));
        } catch (Exception e) {
            Resource resource = new ClassPathResource("static/not-found.html");
            byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
            String htmlContent = new String(bytes);
            return new ResponseEntity<>(htmlContent, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> save(@RequestBody @Valid FacultyRequest faculty) {
        return ResponseEntity.status(201).body(facultyService.createFaculty(faculty.name(), faculty.description()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> update(@PathVariable Long id, @RequestBody @Valid FacultyRequest faculty) {
        return ResponseEntity.ok(facultyService.updateFaculty(id, faculty.name(), faculty.description()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }

}