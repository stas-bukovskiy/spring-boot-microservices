package com.recordsystem.enrollment.filters;

import com.recordsystem.enrollment.dto.UserResponse;
import com.recordsystem.enrollment.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Set;

@RequiredArgsConstructor
public class JwtTokenAuthenticationFilter implements WebFilter {

    private final AuthService authService;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain filterChain) {
        final String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);

        if (!StringUtils.hasText(authHeader) || !StringUtils.startsWithIgnoreCase(authHeader, "Bearer ")) {
            return filterChain.filter(exchange);
        }
        String jwt = authHeader.substring(7);
        UserResponse user;
        try {
            user = authService.authenticate(jwt);
        } catch (ResponseStatusException e) {
            return filterChain.filter(exchange);
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user, jwt, Set.of(new SimpleGrantedAuthority(user.getRole())));
        return filterChain.filter(exchange).contextWrite(
                ReactiveSecurityContextHolder.withAuthentication(authToken)
        );
    }

}
