package com.recordsystem.enrollment.controller;

import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.entity.Enrollment;
import com.recordsystem.enrollment.entity.EnrollmentState;
import com.recordsystem.enrollment.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/enrollment", produces = MediaType.APPLICATION_JSON_VALUE)
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @GetMapping("/state")
    public Mono<String> getState() {
        return enrollmentService.enrollmentState()
                .map(EnrollmentState::name)
                .map(body -> {
                    log.info("get state {}", body);
                    return body;
                })
                .last();
    }

    @PostMapping(value = "/state", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setState(@RequestBody String state) {
        enrollmentService.setState(EnrollmentState.valueOf(state));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<Enrollment> enrollOnCourse(@RequestBody @Validated EnrollmentRequest request) {
        return enrollmentService.enrollOnCourse(request);
    }

    @DeleteMapping("/{id}")
    public Mono<Void> enrollOnCourse(@PathVariable Long id) {
        return enrollmentService.removeEnrolment(id);
    }

}

