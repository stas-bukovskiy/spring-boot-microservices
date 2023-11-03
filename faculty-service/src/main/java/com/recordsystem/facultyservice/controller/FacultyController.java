package com.recordsystem.facultyservice.controller;

import com.recordsystem.facultyservice.model.Faculty;
import com.recordsystem.facultyservice.service.FacultyService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
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

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> save(@RequestBody Faculty faculty) {
        return ResponseEntity.status(201).body(facultyService.save(faculty));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Faculty> update(@PathVariable Long id, @RequestBody Faculty faculty) {
        return ResponseEntity.ok(facultyService.update(id, faculty));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        facultyService.delete(id);
        return ResponseEntity.ok("Deleted successfully");
    }

    @GetMapping(value = "/downloadSchedule", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> downloadSchedule() throws IOException, IOException {

        Resource resource = new ClassPathResource("/schedule/schedule.csv");

        byte[] fileBytes = Files.readAllBytes(resource.getFile().toPath());

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=schedule.csv");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(fileBytes);
    }
}
