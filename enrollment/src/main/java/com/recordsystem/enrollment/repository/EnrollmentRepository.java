package com.recordsystem.enrollment.repository;


import com.recordsystem.enrollment.entity.Enrollment;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface EnrollmentRepository extends ReactiveCrudRepository<Enrollment, Long> {
}
