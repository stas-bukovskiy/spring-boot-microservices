package com.recordsystem.faculty.response;

import lombok.Data;

@Data
public class UserResponse {
    private String id;
    private String username;
    private String role;
    private long createdAt;
}