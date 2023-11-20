package com.recordsystem.auth.service;

import com.recordsystem.auth.exceptions.UserAlreadyExistsException;
import com.recordsystem.auth.repositories.UserRepository;
import com.recordsystem.auth.dto.CreateUserRequest;
import com.recordsystem.auth.exceptions.UserNotFoundException;
import com.recordsystem.auth.models.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.sql.Timestamp;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Mono<User> getUserById(String id) {
        return userRepository.findById(id)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Not found user with id <%s>", id)));
    }

    @Override
    public Mono<User> getUserByUsername(String username) {
        return userRepository.findByEmail(username)
                .switchIfEmpty(Mono.error(new UserNotFoundException("Not found user with username <%s>", username)));
    }

    @Override
    @Transactional(isolation = Isolation.READ_COMMITTED)
    public Mono<Void> createUser(CreateUserRequest request) {
        return userRepository.existsByEmail(request.getUsername())
                .handle((isExist, sink) -> {
                    if (isExist)
                        sink.error(new UserAlreadyExistsException("User already exists with username <%s>", request.getUsername()));
                    else {
                        User userToCreate = User.builder()
                                .email(request.getUsername())
                                .password(passwordEncoder.encode(request.getPassword()))
                                .role(request.getRole())
                                .createdAt(new Timestamp(System.currentTimeMillis()))
                                .build();
                        sink.next(userToCreate);
                    }
                })
                .cast(User.class)
                .flatMap(userRepository::save)
                .then();
    }
}
