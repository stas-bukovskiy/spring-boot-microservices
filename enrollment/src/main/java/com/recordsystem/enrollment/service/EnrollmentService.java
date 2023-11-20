package com.recordsystem.enrollment.service;

import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.entity.EnrollmentState;
import com.recordsystem.enrollment.repository.CourseRepository;
import com.recordsystem.enrollment.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;

@Service
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
        courseRepository.findById(request.courseId())
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Course not found")))
                .zipWith(studentRepository.findById(request.userId())
                        .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"))))
                .map(courseAndStudent -> {
                    courseAndStudent.getT1().getStudents().add(courseAndStudent.getT2());
                    return courseAndStudent.getT1();
                }).subscribe(courseRepository::save);
    }

    public void setState(EnrollmentState state) {
        this.state = state;
        processor.onNext(state);
    }
}
