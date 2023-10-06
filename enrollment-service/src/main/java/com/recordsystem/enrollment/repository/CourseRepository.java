package com.recordsystem.enrollment.repository;

import com.recordsystem.enrollment.entity.Course;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository extends ReactiveCrudRepository<Course, Long> {

}
