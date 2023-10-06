package com.recordsystem.enrollment.service;


import com.recordsystem.enrollment.dto.UserResponse;

public interface AuthService {

    UserResponse authenticate(String jwt);
}
