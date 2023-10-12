package com.recordsystem.enrollment.repository;

import com.recordsystem.enrollment.entity.Student;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends ReactiveCrudRepository<Student, String> {
}
