package com.recordsystem.userservice.controller;

import com.recordsystem.userservice.dto.UserDto;
import com.recordsystem.userservice.request.LoginRequest;
import com.recordsystem.userservice.request.SignupRequest;
import com.recordsystem.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    @PostMapping("/auth/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(authService.login(loginRequest));
    }

    @PostMapping("/auth/sign-up")
    public ResponseEntity<UserDto> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerUser(signUpRequest));
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/auth/sign-up/admin")
    public ResponseEntity<UserDto> registerAdmin(@Valid @RequestBody SignupRequest signUpRequest) {
        return ResponseEntity.ok(authService.registerAdmin(signUpRequest));
    }

}
