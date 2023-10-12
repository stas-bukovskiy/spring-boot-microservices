package com.recordsystem.facultyservice.controller;

import com.recordsystem.facultyservice.model.Discipline;
import com.recordsystem.facultyservice.model.Faculty;
import com.recordsystem.facultyservice.service.DisciplineService;
import com.recordsystem.facultyservice.service.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/discipline")
public class DisciplineController {

    private final DisciplineService disciplineService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Discipline>> getAllFaculties() {
        return ResponseEntity.ok(disciplineService.getAllDisciplines());
    }

    @SneakyThrows
    @GetMapping(value = "/{id}",produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> getFacultyById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(disciplineService.getDisciplineById(id));
        } catch (Exception e) {
            Resource resource = new ClassPathResource("static/not-found.html");
            byte[] bytes = Files.readAllBytes(Paths.get(resource.getURI()));
            String htmlContent = new String(bytes);
            return new ResponseEntity<>(htmlContent, HttpStatus.NOT_FOUND);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Discipline> save(@RequestBody Discipline discipline) {
        return ResponseEntity.status(201).body(disciplineService.save(discipline));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Discipline> update(@PathVariable Long id, @RequestBody Discipline discipline) {
        return ResponseEntity.ok(disciplineService.update(id, discipline));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        disciplineService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
