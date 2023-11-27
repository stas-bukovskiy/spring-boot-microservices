package com.recordsystem.enrollment.service.impl;


import com.recordsystem.enrollment.dto.StudentDto;
import com.recordsystem.enrollment.service.StudentService;
import com.recordsystem.user.v1.UserRequest;
import com.recordsystem.user.v1.UserRole;
import com.recordsystem.user.v1.UserServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final UserServiceGrpc.UserServiceBlockingStub userServiceGrpc;

    @Override
    public Optional<StudentDto> getStudentById(Long studentId) {
        try {
            var user = userServiceGrpc.get(UserRequest.newBuilder().setId(studentId).build());
            if (!user.getRole().equals(UserRole.USER)) {
                return Optional.empty();
            }

            return Optional.of(StudentDto.builder()
                    .id(user.getId())
                    .firstName(user.getFirstName())
                    .lastName(user.getLastName())
                    .email(user.getEmail())
                    .role(com.recordsystem.enrollment.dto.UserRole.valueOf(user.getRole().name()))
                    .createdAt(new Timestamp(user.getCreatedAt().getSeconds()))
                    .build());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Status.Code.NOT_FOUND)) {
                return Optional.empty();
            }

            log.error("Error while getting student by id: {}", studentId, e);
            throw new RuntimeException("error retrieving student by id", e);
        }
    }
}
