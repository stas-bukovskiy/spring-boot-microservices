package com.recordsystem.auth.web.grpc;


import com.google.protobuf.Any;
import com.google.protobuf.Timestamp;
import com.google.rpc.Code;
import com.google.rpc.ErrorInfo;
import com.recordsystem.auth.exceptions.UserNotFoundException;
import com.recordsystem.auth.service.UserService;

import com.recordsystem.user.v1.User;
import com.recordsystem.user.v1.UserRequest;
import com.recordsystem.user.v1.UserRole;
import com.recordsystem.user.v1.UserServiceGrpc;
import io.grpc.protobuf.StatusProto;
import io.grpc.stub.StreamObserver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserGrpcService  extends UserServiceGrpc.UserServiceImplBase{

    private final UserService userService;

    @Override
    public void get(UserRequest request, StreamObserver<User> responseObserver) {
        log.info("[USER_GRPC_SERVICE] Request received: {}", request);
        userService.getUserById(request.getId())
                .subscribe(
                        (user) -> {
                            responseObserver.onNext(toGrpcUser(user));
                            responseObserver.onCompleted();
                            log.info("[USER_GRPC_SERVICE] Request processed successfullty {}", user);
                        },
                        (e) -> {
                            com.google.rpc.Status status = null;
                            if (e instanceof UserNotFoundException) {
                                status = com.google.rpc.Status.newBuilder()
                                        .setCode(com.google.rpc.Code.NOT_FOUND.getNumber())
                                        .setMessage(e.getMessage())
                                        .build();
                            } else {
                                status = com.google.rpc.Status.newBuilder()
                                        .setCode(Code.INTERNAL.getNumber())
                                        .setMessage(e.getMessage())
                                        .build();
                            }
                            responseObserver.onError(StatusProto.toStatusRuntimeException(status));
                            log.info("[USER_GRPC_SERVICE] Request processed with error: {}", e.getMessage());
                        }
                );
    }

    private User toGrpcUser(com.recordsystem.auth.models.User user) {
        return User.newBuilder()
                .setId(user.getId())
                .setFirstName(user.getFirstName())
                .setLastName(user.getLastName())
                .setEmail(user.getEmail())
                .setRole(UserRole.valueOf(user.getRole().name()))
                .setCreatedAt(Timestamp.newBuilder().setSeconds(user.getCreatedAt().getTime()).build())
                .build();
    }
}
