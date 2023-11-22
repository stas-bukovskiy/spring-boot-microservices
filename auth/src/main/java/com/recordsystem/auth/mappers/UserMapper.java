package com.recordsystem.auth.mappers;

import com.recordsystem.auth.dto.UserDto;
import com.recordsystem.auth.models.User;

public final class UserMapper {
    private UserMapper() {

    }

    public static UserDto of(User user) {
        return UserDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .username(user.getUsername())
                .role(user.getRole().name())
                .createdAt(user.getCreatedAt().getTime())
                .build();
    }
}
