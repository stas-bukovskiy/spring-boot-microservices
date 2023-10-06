package com.recordsystem.enrollment.controller;

import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.service.EnrollmentService;
import com.recordsystem.enrollment.entity.EnrollmentState;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/enrollment")
@RequiredArgsConstructor
@Slf4j
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

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
    @PreAuthorize("hasAuthority('ADMIN')")
    public void setState(@RequestBody String state) {
        enrollmentService.setState(EnrollmentState.valueOf(state));
    }

    @PostMapping("/enroll")
    public void enrollOnCourse(@RequestBody EnrollmentRequest request) {
        enrollmentService.enrollOnCourse(request);
    }
}

