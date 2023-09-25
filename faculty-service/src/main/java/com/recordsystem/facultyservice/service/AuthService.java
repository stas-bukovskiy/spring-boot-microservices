package com.recordsystem.facultyservice.service;

import com.recordsystem.facultyservice.response.UserResponse;

public interface AuthService {

    UserResponse authenticate(String jwt);
}
