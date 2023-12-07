package com.recordsystem.enrollment.service.impl;

import com.recordsystem.enrollment.dto.DisciplineDto;
import com.recordsystem.enrollment.dto.EnrollmentRequest;
import com.recordsystem.enrollment.dto.StudentDto;
import com.recordsystem.enrollment.entity.Enrollment;
import com.recordsystem.enrollment.entity.EnrollmentState;
import com.recordsystem.enrollment.service.CourseService;
import com.recordsystem.enrollment.repository.EnrollmentRepository;
import com.recordsystem.enrollment.service.EnrollmentService;
import com.recordsystem.enrollment.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.publisher.ReplayProcessor;
import reactor.util.function.Tuple2;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {

    @Value("${enrollment.state}")
    private EnrollmentState state;

    private final ReplayProcessor<EnrollmentState> processor = ReplayProcessor.create(1);

    private final JmsTemplate jmsTemplate;

    private final CourseService courseService;
    private final StudentService studentService;
    private final EnrollmentRepository enrollmentRepository;

    public Flux<EnrollmentState> enrollmentState() {
        return Flux.create(sink -> {
            sink.next(state);
            processor.subscribe(sink::next);
        });
    }

    @Transactional
    public Mono<Enrollment> enrollOnCourse(EnrollmentRequest request) {
        return enrollmentRepository.existsByCourseIdAndStudentId(request.disciplineId(), request.studentId())
                .handle((isExist, sink) -> {
                    if (isExist) {
                        sink.error(new ResponseStatusException(HttpStatus.CONFLICT, "Student already enrolled on this discipline"));
                    } else {
                        sink.next(false);
                    }
                })
                .thenReturn(studentService.getStudentById(request.studentId()))
                .handle((student, sink) -> {
                    if (student.isPresent()) {
                        sink.next(student.get());
                    } else {
                        sink.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Student not found"));
                    }
                })
                .cast(StudentDto.class)
                .zipWith(Mono.fromCallable(() -> {
                    var discipline = courseService.getDisciplineById(request.disciplineId());
                    if (discipline.isPresent()) {
                        return discipline.get();
                    } else {
                        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Discipline not found");
                    }
                }))
                .map(tuple -> {
                    StudentDto student = tuple.getT1();
                    DisciplineDto discipline = tuple.getT2();

                    notifyAboutEnrollment(student, discipline);

                    return Enrollment.builder()
                            .studentId(request.studentId())
                            .courseId(request.disciplineId())
                            .build();
                })
                .flatMap(enrollmentRepository::save);
    }

    private void notifyAboutEnrollment(StudentDto student, DisciplineDto discipline) {
        String message = "Student " + student.getEmail() + " enrolled on discipline " + discipline.getName();
        jmsTemplate.convertAndSend("system_events", message);
    }


    @Override
    public Mono<Void> removeEnrolment(Long id) {
        return enrollmentRepository.deleteById(id);
    }

    public void setState(EnrollmentState state) {
        this.state = state;
        processor.onNext(state);
    }
}
