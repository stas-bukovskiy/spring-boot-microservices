package com.recordsystem.gatewayservice.config;

import com.recordsystem.gatewayservice.filter.AuthJwtGatewayFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

    private final AuthJwtGatewayFilter authJwtGatewayFilter;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("faculty-service", p -> p
                        .path("/faculty")
                        .filters(f -> f
                                .rewritePath("\\/faculty", "/api/v1/faculty")
                                .filter(authJwtGatewayFilter.apply(new AuthJwtGatewayFilter.Config())
                        ))
                        .uri("http://localhost:8001"))
                .route("discipline-service", p -> p
                        .path("/discipline")
                        .filters(f -> f
                                .rewritePath("\\/discipline", "/api/v1/discipline")
                                .filter(authJwtGatewayFilter.apply(new AuthJwtGatewayFilter.Config())
                        ))
                        .uri("http://localhost:8001"))
                .route("schedule-service", p -> p
                        .path("/schedule")
                        .filters(f -> f
                                .rewritePath("\\/schedule", "/api/v1/schedule")
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
                .route("enrollment-service", p -> p
                        .path("/enrollment/**")
                        .filters(f -> f
                                .rewritePath("\\/enrollment", "/api/v1/enrollment"))
                        .uri("http://localhost:8003"))
                .route("notification-service", p -> p
                        .path("/notification/**")
                        .filters(f -> f
                                .rewritePath("\\/notification", "/notification"))
                        .uri("http://localhost:8004"))
                .route("admin-service", p -> p
                        .path("/admin/**")
                        .filters(f -> f
                                .rewritePath("\\/admin", "/admin"))
                        .uri("http://localhost:8002"))
                .build();
    }
}
