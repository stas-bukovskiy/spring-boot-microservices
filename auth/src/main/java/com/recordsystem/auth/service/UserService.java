package com.recordsystem.auth.service;

import com.recordsystem.auth.dto.CreateUserRequest;
import com.recordsystem.auth.models.User;
import reactor.core.publisher.Mono;

public interface UserService {
    Mono<User> getUserById(Long id);

    Mono<User> getUserByUsername(String username);

    Mono<Void> createUser(CreateUserRequest request);
}
