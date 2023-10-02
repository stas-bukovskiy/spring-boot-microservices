package com.recordsystem.facultyservice.config;

import com.recordsystem.facultyservice.exception.UserNotFoundException;
import com.recordsystem.facultyservice.response.UserResponse;
import com.recordsystem.facultyservice.service.AuthService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Set;

@Component
public class JwtUsernamePasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthService authService;

    public JwtUsernamePasswordAuthenticationFilter(AuthService authService, AuthenticationManager authenticationManager) {
        super(authenticationManager);
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String token = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();

        UserResponse user;
        try {
            user = authService.authenticate(token);
        } catch (UserNotFoundException e) {
            throw new UsernameNotFoundException("invalid token");
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(token, user, Set.of(new SimpleGrantedAuthority(user.getRole())));
        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails((HttpServletRequest) request));
        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);

        chain.doFilter(request, response);
    }
}