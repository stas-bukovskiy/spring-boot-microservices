package com.recordsystem.userservice.service;

import com.recordsystem.userservice.exceptions.InvalidTokenException;
import com.recordsystem.userservice.exceptions.UserNotFoundException;
import com.recordsystem.userservice.dto.AuthenticationRequest;
import com.recordsystem.userservice.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final ReactiveAuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;

    @Override
    public Mono<String> loginUser(AuthenticationRequest authRequestMono) {
        return authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(authRequestMono.getUsername(), authRequestMono.getPassword()))
                .map(jwtService::generateToken);
    }

    @Override
    public Mono<User> verifyToken(String tokenToVerify) {
        return Mono.just(jwtService.isTokenValid(tokenToVerify))
                .handle((isValid, sink) -> {
                    if (isValid) {
                        String username = jwtService.extractUsername(tokenToVerify);
                        sink.next(username);
                    } else {
                        sink.error(new InvalidTokenException());
                    }
                }).cast(String.class)
                .flatMap(userService::getUserByUsername)
                .onErrorMap(e -> e instanceof UserNotFoundException,
                        e -> new InvalidTokenException());
    }

}
