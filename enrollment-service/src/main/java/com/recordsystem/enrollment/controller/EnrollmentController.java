package com.recordsystem.enrollment.controller;

import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.service.EnrollmentService;
import com.recordsystem.enrollment.entity.EnrollmentState;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1/enrollment")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @GetMapping("/state")
    public Flux<String> getState() {
        return enrollmentService.enrollmentState().map(EnrollmentState::name);
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

