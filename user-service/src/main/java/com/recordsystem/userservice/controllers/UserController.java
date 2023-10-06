package com.recordsystem.userservice.controllers;


import com.recordsystem.userservice.dto.CreateUserRequest;
import com.recordsystem.userservice.dto.UserDto;
import com.recordsystem.userservice.mappers.UserMapper;
import com.recordsystem.userservice.models.UserRole;
import com.recordsystem.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
@Slf4j
public class UserController {

    private final UserService userService;


    @PreAuthorize("isAuthenticated()")
    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<UserDto>> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(UserMapper::of)
                .map(ResponseEntity::ok);
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> createUser(@RequestBody @Validated CreateUserRequest request) {
        request.setRole(UserRole.USER);
        return userService.createUser(request)
                .then(Mono.fromCallable(() -> ResponseEntity.status(HttpStatus.CREATED).build()));
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @PostMapping(value = "/register/admin", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<Void>> createAdmin(@RequestBody @Validated CreateUserRequest request) {
        request.setRole(UserRole.ADMIN);
        return userService.createUser(request)
                .then(Mono.fromCallable(() -> ResponseEntity.status(HttpStatus.CREATED).build()));
    }
}
