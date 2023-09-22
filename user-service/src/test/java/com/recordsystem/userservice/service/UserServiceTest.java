package com.recordsystem.userservice.service;

import com.recordsystem.userservice.exceptions.UserAlreadyExistsException;
import com.recordsystem.userservice.exceptions.UserNotFoundException;
import com.recordsystem.userservice.models.User;
import com.recordsystem.userservice.repositories.UserRepository;
import com.recordsystem.userservice.dto.CreateUserRequest;
import com.recordsystem.userservice.util.RandomModelUtil;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.sql.Timestamp;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest(classes = {UserServiceImpl.class, BCryptPasswordEncoder.class})
class UserServiceTest {

    @MockBean
    private UserRepository userRepository;
    @Autowired
    private UserServiceImpl userService;

    private static User toUser(CreateUserRequest request) {
        return User.builder()
                .id(UUID.randomUUID().toString())
                .email(request.getUsername())
                .password(request.getPassword())
                .role(request.getRole())
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    void testGetUserByIdWithValidId_shouldReturnUser() {
        final User user = RandomModelUtil.randomUser();
        when(userRepository.findById(user.getId())).thenReturn(Mono.just(user));

        Mono<User> gotUser = userService.getUserById(user.getId());

        StepVerifier.create(gotUser)
                .expectNext(user)
                .verifyComplete();
        verify(userRepository, times(1)).findById(user.getId());
    }

    @Test
    void testGetUserByIdWithValidId_shouldThrowException() {
        final String notExistingUserId = UUID.randomUUID().toString();
        when(userRepository.findById(notExistingUserId)).thenReturn(Mono.empty());

        Mono<User> gotUser = userService.getUserById(notExistingUserId);

        StepVerifier.create(gotUser)
                .verifyError(UserNotFoundException.class);
        verify(userRepository, times(1)).findById(notExistingUserId);
    }

    @Test
    void testGetUserByUsernameWithValidUsername_shouldReturnUser() {
        final User user = RandomModelUtil.randomUser();
        when(userRepository.findByEmail(user.getUsername())).thenReturn(Mono.just(user));

        Mono<User> gotUser = userService.getUserByUsername(user.getUsername());

        StepVerifier.create(gotUser)
                .expectNext(user)
                .verifyComplete();
        verify(userRepository, times(1)).findByEmail(user.getUsername());
    }

    @Test
    void testGetUserByIdWithNotExistingUsername_shouldThrowException() {
        final String notExistingUsername = RandomModelUtil.randomString(10);
        when(userRepository.findByEmail(notExistingUsername)).thenReturn(Mono.empty());

        Mono<User> gotUser = userService.getUserByUsername(notExistingUsername);

        StepVerifier.create(gotUser)
                .verifyError(UserNotFoundException.class);
        verify(userRepository, times(1)).findByEmail(notExistingUsername);
    }

    @Test
    void testCreateUserWithValidRequest_shouldCreateUser() {
        final CreateUserRequest request = RandomModelUtil.randomCreateUserRequest();
        final User savedUser = toUser(request);
        when(userRepository.existsByEmail(request.getUsername())).thenReturn(Mono.just(Boolean.FALSE));
        when(userRepository.save(any())).thenReturn(Mono.just(savedUser));

        Mono<Void> createdUser = userService.createUser(request);

        StepVerifier.create(createdUser)
                .expectNextCount(0)
                .verifyComplete();
        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(1)).save(any());
    }

    @Test
    void testCreateUserWithNotUniqueUsername_shouldThrowException() {
        final CreateUserRequest request = RandomModelUtil.randomCreateUserRequest();
        when(userRepository.existsByEmail(request.getUsername())).thenReturn(Mono.just(Boolean.TRUE));

        Mono<Void> createdUser = userService.createUser(request);

        StepVerifier.create(createdUser)
                .verifyError(UserAlreadyExistsException.class);
        verify(userRepository, times(1)).existsByEmail(any());
        verify(userRepository, times(0)).save(any());
    }
}