package com.recordsystem.service;

import com.recordsystem.response.UserResponse;

import java.util.Optional;

public interface AuthService {

    UserResponse authenticate(String jwt);
}
