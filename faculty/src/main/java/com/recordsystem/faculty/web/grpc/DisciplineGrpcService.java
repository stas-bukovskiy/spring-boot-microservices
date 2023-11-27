package com.recordsystem.faculty.web.grpc;

import com.recordsystem.faculty.v1.Discipline;
import com.recordsystem.faculty.v1.DisciplineRequest;
import com.recordsystem.faculty.v1.DisciplineServiceGrpc;
import com.recordsystem.faculty.v1.Faculty;
import com.recordsystem.faculty.repository.DisciplineRepository;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DisciplineGrpcService extends DisciplineServiceGrpc.DisciplineServiceImplBase {

    private final DisciplineRepository disciplineRepository;

    @Override
    public void get(DisciplineRequest request, StreamObserver<Discipline> responseObserver) {
        log.info("[DISCIPLINE_GRPC_SERVICE] Request received: {}", request);
        Optional<com.recordsystem.faculty.model.Discipline> discipline = disciplineRepository.findById(request.getId());
        if (discipline.isPresent()) {
            responseObserver.onNext(toGrpcDiscipline(discipline.get()));
            responseObserver.onCompleted();
            log.info("[DISCIPLINE_GRPC_SERVICE] Request processed successfully {}", discipline.get());
        } else {
            var status = com.google.rpc.Status.newBuilder()
                    .setCode(com.google.rpc.Code.NOT_FOUND.getNumber())
                    .setMessage("Discipline not found")
                    .build();
            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
            log.info("[DISCIPLINE_GRPC_SERVICE] Request processed with error: {}", "Discipline not found");
        }
    }

    private Discipline toGrpcDiscipline(com.recordsystem.faculty.model.Discipline discipline) {
        return Discipline.newBuilder()
                .setId(discipline.getId())
                .setName(discipline.getName())
                .setDescription(discipline.getDescription())
                .setFaculty(Faculty.newBuilder()
                        .setId(discipline.getFaculty().getId())
                        .setName(discipline.getFaculty().getName())
                        .setDescription(discipline.getFaculty().getDescription())
                        .build())
                .build();
    }
}
