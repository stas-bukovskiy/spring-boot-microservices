package com.recordsystem.enrollment.repository;


import com.recordsystem.enrollment.entity.Enrollment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface EnrollmentRepository extends ReactiveCrudRepository<Enrollment, Long> {
    Mono<Boolean> existsByCourseIdAndStudentId(Long courseId, Long studentId);
}
