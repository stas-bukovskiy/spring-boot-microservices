package com.recordsystem.enrollment.service;

import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.entity.Course;
import com.recordsystem.enrollment.entity.EnrollmentState;
import com.recordsystem.enrollment.entity.Student;
import com.recordsystem.enrollment.repository.CourseRepository;
import com.recordsystem.enrollment.repository.StudentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import reactor.core.publisher.Flux;
import reactor.core.publisher.ReplayProcessor;

@RequiredArgsConstructor
public class EnrollmentService {
    @Value("${enrollment.state}")
    private EnrollmentState state;
    private final ReplayProcessor<EnrollmentState> processor = ReplayProcessor.create(1);

    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;

    public Flux<EnrollmentState> enrollmentState() {
        return Flux.create(sink -> {
            sink.next(state);
            processor.subscribe(sink::next);
        });
    }

    public void enrollOnCourse(EnrollmentRequest request) {
        Student user = studentRepository.findById(request.userId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        Course course = courseRepository.findById(request.courseId())
                .orElseThrow(() -> new EntityNotFoundException("Course not found"));

        course.getStudents().add(user);
        courseRepository.save(course);
    }

    public void setState(EnrollmentState state) {
        this.state = state;
        processor.onNext(state);
    }
}
