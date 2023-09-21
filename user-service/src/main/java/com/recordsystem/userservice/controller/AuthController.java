package com.recordsystem.userservice.controller;

import com.recordsystem.userservice.payload.request.LoginRequest;
import com.recordsystem.userservice.payload.request.SignupRequest;
import com.recordsystem.userservice.payload.response.MessageResponse;
import com.recordsystem.userservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
        MessageResponse messageResponse = authService.signup(signUpRequest);
        if (messageResponse.getStatus() == 400) {
            return ResponseEntity.badRequest().body(messageResponse.getMessage());
        }
        else if (messageResponse.getStatus() == 200) {
            return ResponseEntity.ok(messageResponse.getMessage());
        }
        return ResponseEntity.internalServerError().body(messageResponse.getMessage());
    }

}
