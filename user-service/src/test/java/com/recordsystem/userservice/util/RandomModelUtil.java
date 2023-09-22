package com.recordsystem.userservice.util;


import com.recordsystem.userservice.models.User;
import com.recordsystem.userservice.models.UserRole;
import com.recordsystem.userservice.dto.CreateUserRequest;

import java.security.SecureRandom;
import java.sql.Timestamp;
import java.util.Random;
import java.util.UUID;

public final class RandomModelUtil {

    private static final Random random = new SecureRandom();

    private RandomModelUtil() {
    }

    public static User randomUser() {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .email(randomString(10))
                .password(randomString(10))
                .role(UserRole.USER)
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    public static String randomString(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    public static CreateUserRequest randomCreateUserRequest() {
        return new CreateUserRequest(RandomModelUtil.randomString(10), RandomModelUtil.randomString(10), UserRole.USER);
    }
}
