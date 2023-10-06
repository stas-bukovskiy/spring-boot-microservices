package com.recordsystem.enrollment.repository;

import com.recordsystem.enrollment.entity.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> getAllByFacultyId(Long facultyId);
}
