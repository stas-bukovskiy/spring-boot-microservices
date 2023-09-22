package com.recordsystem.userservice.service;

import com.recordsystem.userservice.dto.CreateUserRequest;
import com.recordsystem.userservice.models.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> getUserById(String id);

    Mono<User> getUserByUsername(String username);

    Mono<Void> createUser(CreateUserRequest request);
}
