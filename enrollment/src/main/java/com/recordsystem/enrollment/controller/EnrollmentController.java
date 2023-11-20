package com.recordsystem.enrollment.controller;

import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.entity.EnrollmentState;
import com.recordsystem.enrollment.service.EnrollmentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/enrollment")
@Slf4j
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    private final RestTemplate restTemplate;


    @Autowired
    public EnrollmentController(EnrollmentService enrollmentService, RestTemplate restTemplate) {
        this.enrollmentService = enrollmentService;
        this.restTemplate = restTemplate;
    }

    @GetMapping("/state")
    public Flux<ResponseEntity<String>> getState() {
        return enrollmentService.enrollmentState()
                .map(EnrollmentState::name)
                .map(body -> {
                    log.info("get state {}", body);
                    return ResponseEntity.ok(body);
                });
    }

    @PostMapping("/state")
    public void setState(@RequestBody String state) {
        enrollmentService.setState(EnrollmentState.valueOf(state));
    }

    @PostMapping("/enroll")
    public void enrollOnCourse(@RequestBody EnrollmentRequest request) {
        enrollmentService.enrollOnCourse(request);
    }

//    @GetMapping(value = "/facultiesList", produces = MediaType.APPLICATION_JSON_VALUE)
//    public String getFacultiesList() {
//        String facultyServiceURL = "http://facultyService/api/v1/faculty/getAll";
//
////        return eurekaClient.getApplication("faculty-service").getName();
//        return restTemplate.getForObject(facultyServiceURL, String.class);
//    }
}

