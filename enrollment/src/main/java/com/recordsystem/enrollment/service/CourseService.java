package com.recordsystem.enrollment.service;

import reactor.core.publisher.Mono;

public interface CourseService {
    Mono<Boolean> existsById(Long courseId);
}
