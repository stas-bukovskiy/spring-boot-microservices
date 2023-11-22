package com.recordsystem.enrollment.service;


import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.entity.Enrollment;
import com.recordsystem.enrollment.entity.EnrollmentState;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface EnrollmentService {
    Flux<EnrollmentState> enrollmentState();

    void setState(EnrollmentState enrollmentState);

    Mono<Enrollment> enrollOnCourse(EnrollmentRequest request);

    Mono<Void> removeEnrolment(Long id);
}
