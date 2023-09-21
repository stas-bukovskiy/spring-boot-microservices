package com.recordsystem.userservice.service;

import com.recordsystem.userservice.dto.UserDto;
import com.recordsystem.userservice.request.LoginRequest;
import com.recordsystem.userservice.request.SignupRequest;

public interface AuthService {

    String login(LoginRequest loginRequest);

    UserDto registerUser(SignupRequest signupRequest);

    UserDto registerAdmin(SignupRequest signUpRequest);
}
