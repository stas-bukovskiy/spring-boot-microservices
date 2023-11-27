package com.recordsystem.enrollment.service.impl;

import com.recordsystem.enrollment.dto.DisciplineDto;
import com.recordsystem.enrollment.dto.FacultyDto;
import com.recordsystem.enrollment.service.CourseService;
import com.recordsystem.faculty.v1.DisciplineRequest;
import com.recordsystem.faculty.v1.DisciplineServiceGrpc;
import io.grpc.Status;
import io.grpc.StatusRuntimeException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class DisciplineServiceImpl implements CourseService {

    private final DisciplineServiceGrpc.DisciplineServiceBlockingStub disciplineServiceGrpc;


    @Override
    public Optional<DisciplineDto> getDisciplineById(Long discipleID) {
        try {
            var discipline = disciplineServiceGrpc.get(DisciplineRequest.newBuilder().setId(discipleID).build());

            return Optional.of(DisciplineDto.builder()
                    .id(discipline.getId())
                    .name(discipline.getName())
                    .description(discipline.getDescription())
                    .faculty(FacultyDto.builder()
                            .id(discipline.getFaculty().getId())
                            .name(discipline.getFaculty().getName())
                            .description(discipline.getFaculty().getDescription())
                            .build())
                    .build());
        } catch (StatusRuntimeException e) {
            if (e.getStatus().getCode().equals(Status.Code.NOT_FOUND)) {
                return Optional.empty();
            } else {
                log.error("Error while getting discipline by id: {}", disciplineServiceGrpc, e);
                throw new RuntimeException("error retrieving student by id", e);
            }

        }
    }
}
