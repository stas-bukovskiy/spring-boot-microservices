package com.recordsystem.gatewayservice.config;

import com.recordsystem.gatewayservice.filter.AuthJwtGatewayFilter;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.circuitbreaker.resilience4j.ReactiveResilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

    private final AuthJwtGatewayFilter authJwtGatewayFilter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(p -> p
                        .path("/get")
                        .filters(f -> f.circuitBreaker(config -> config
                                        .setName("myCircuitBreaker")
                                        .setFallbackUri("forward:/fallback")))
                        .uri("lb://localhost:8001/api/v1"))
                .route("faculty-service", p -> p
                        .path("/faculty")
                        .filters(f -> f
                                .rewritePath("\\/faculty", "/api/v1/faculty")
                                .filter(authJwtGatewayFilter.apply(new AuthJwtGatewayFilter.Config())
                        ))
                        .uri("http://localhost:8001"))
                .route("auth-service", p -> p
                        .path("/auth/**")
                        .filters(f -> f
                                .rewritePath("\\/auth", "/api/v1/auth"))
                        .uri("http://localhost:8000"))
                .route("user-service", p -> p
                        .path("/users/**")
                        .filters(f -> f
                                .rewritePath("\\/users", "/api/v1/users"))
                        .uri("http://localhost:8000"))
                .build();
    }

    @Bean
    public Customizer<ReactiveResilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> new Resilience4JConfigBuilder(id)
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults())
                .timeLimiterConfig(TimeLimiterConfig.custom().timeoutDuration(Duration.ofMillis(200)).build())
                .build());
    }
}
