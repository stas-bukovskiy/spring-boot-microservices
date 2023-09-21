package com.recordsystem.userservice.service;

import com.recordsystem.userservice.payload.request.LoginRequest;
import com.recordsystem.userservice.payload.request.SignupRequest;
import com.recordsystem.userservice.payload.response.JwtResponse;
import com.recordsystem.userservice.payload.response.MessageResponse;

public interface AuthService {

    JwtResponse login(LoginRequest loginRequest);

    MessageResponse signup(SignupRequest signupRequest);
}
