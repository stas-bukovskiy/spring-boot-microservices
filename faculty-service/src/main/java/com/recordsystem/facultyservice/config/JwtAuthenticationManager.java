package com.recordsystem.facultyservice.config;

import com.recordsystem.facultyservice.exception.UserNotFoundException;
import com.recordsystem.facultyservice.response.UserResponse;
import com.recordsystem.facultyservice.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationManager implements AuthenticationManager {

    private final AuthService authService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getCredentials().toString();

        UserResponse user;
        try {
            user = authService.authenticate(token);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("invalid token");
        }

        return new UsernamePasswordAuthenticationToken(
                user, token, Set.of(new SimpleGrantedAuthority(user.getRole()))
        );
    }
}