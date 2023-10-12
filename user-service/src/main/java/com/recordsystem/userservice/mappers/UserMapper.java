package com.recordsystem.userservice.mappers;

import com.recordsystem.userservice.dto.UserDto;
import com.recordsystem.userservice.models.User;

public final class UserMapper {
    private UserMapper() {

    }

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt().getTime())
                .build();
    }
}
