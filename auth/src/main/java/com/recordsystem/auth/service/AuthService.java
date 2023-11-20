package com.recordsystem.auth.service;

import com.recordsystem.auth.dto.AuthenticationRequest;
import com.recordsystem.auth.models.User;
import reactor.core.publisher.Mono;

public interface AuthService {
    Mono<String> loginUser(AuthenticationRequest authRequestMono);

    Mono<User> verifyToken(String tokenToVerify);
}
