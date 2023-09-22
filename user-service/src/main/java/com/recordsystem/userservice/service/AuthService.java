package com.recordsystem.userservice.service;

import com.recordsystem.userservice.dto.AuthenticationRequest;
import com.recordsystem.userservice.models.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> loginUser(AuthenticationRequest authRequestMono);

    Mono<User> verifyToken(String tokenToVerify);
}
