package com.recordsystem.enrollment.config;

import com.recordsystem.enrollment.filters.JwtTokenAuthenticationFilter;
import com.recordsystem.enrollment.service.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UserDetailsRepositoryReactiveAuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
@Configuration
public class SecurityConfig {


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http,
                                                         ReactiveAuthenticationManager authenticationManager,
                                                         AuthService authService) {
        return http
                .exceptionHandling(exceptionHandlingSpec -> exceptionHandlingSpec.authenticationEntryPoint((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED))
                ).accessDeniedHandler((swe, e) ->
                        Mono.fromRunnable(() -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN))
                ))
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .formLogin(ServerHttpSecurity.FormLoginSpec::disable)
                .httpBasic(ServerHttpSecurity.HttpBasicSpec::disable)
                .authenticationManager(authenticationManager)
                .securityContextRepository(NoOpServerSecurityContextRepository.getInstance())
                .addFilterAt(new JwtTokenAuthenticationFilter(authService), SecurityWebFiltersOrder.AUTHENTICATION)
                .authorizeExchange(authorizeExchangeSpec -> authorizeExchangeSpec
                        .anyExchange().permitAll()
                )
                .build();
    }

    @Bean
    public ReactiveAuthenticationManager reactiveAuthenticationManager(ReactiveUserDetailsService userDetailsService,
                                                                       PasswordEncoder passwordEncoder) {
        UserDetailsRepositoryReactiveAuthenticationManager authenticationManager = new UserDetailsRepositoryReactiveAuthenticationManager(userDetailsService);
        authenticationManager.setPasswordEncoder(passwordEncoder);
        return authenticationManager;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        return (username) -> null;
    }
}
