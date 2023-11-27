package com.recordsystem.faculty.web.http;

import com.recordsystem.faculty.dto.DisciplineRequest;
import com.recordsystem.faculty.model.Discipline;
import com.recordsystem.faculty.service.DisciplineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Discipline> save(@RequestBody @Valid DisciplineRequest discipline) {
        return ResponseEntity.status(201).body(disciplineService.createDiscipline(discipline.name(), discipline.description(), discipline.facultyId()));
    }

    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Discipline> update(@PathVariable Long id, @RequestBody DisciplineRequest discipline) {
        return ResponseEntity.ok(disciplineService.updateDiscipline(id, discipline.name(), discipline.description(), discipline.facultyId()));
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        disciplineService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }
}
