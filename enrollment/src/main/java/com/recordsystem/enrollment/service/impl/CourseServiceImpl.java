package com.recordsystem.enrollment.service.impl;

import com.recordsystem.enrollment.service.CourseService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class CourseServiceImpl implements CourseService {
    @Override
    public Mono<Boolean> existsById(Long courseId) {
        return Mono.just(Boolean.TRUE);
    }
}
