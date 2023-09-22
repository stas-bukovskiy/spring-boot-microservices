package com.recordsystem.userservice.service;

import org.springframework.security.core.Authentication;

public interface JwtService {
    String generateToken(Authentication authentication);

    Authentication extractAuthentication(String token);

    String extractUsername(String token);

    boolean isTokenValid(String token);
}
