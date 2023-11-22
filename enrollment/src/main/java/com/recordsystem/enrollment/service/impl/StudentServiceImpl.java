package com.recordsystem.enrollment.service.impl;


import com.recordsystem.enrollment.dto.StudentDto;
import com.recordsystem.enrollment.dto.UserRole;
import com.recordsystem.enrollment.service.StudentService;
import com.recordsystem.user.v1.UserRequest;
import com.recordsystem.user.v1.UserServiceGrpc;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final UserServiceGrpc.UserServiceBlockingStub userServiceGrpc;

    @Override
    public StudentDto getStudentById(Long studentId) {
        var user = userServiceGrpc.get(UserRequest.newBuilder().setId(studentId).build());
        return StudentDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(UserRole.valueOf(user.getRole().name()))
                .createdAt(new Timestamp(user.getCreatedAt().getSeconds()))
                .build();
    }
}
