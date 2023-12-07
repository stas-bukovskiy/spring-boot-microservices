package com.recordsystem.gatewayservice.config;

import com.recordsystem.gatewayservice.filter.AuthJwtGatewayFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RouteConfiguration {

    private final AuthJwtGatewayFilter authJwtGatewayFilter;

    @Value("${service.uri.faculty}")
    private String facultyServiceUri;
    @Value("${service.uri.discipline}")
    private String disciplineServiceUri;
    @Value("${service.uri.schedule}")
    private String scheduleServiceUri;
    @Value("${service.uri.auth}")
    private String authServiceUri;
    @Value("${service.uri.user}")
    private String userServiceUri;
    @Value("${service.uri.enrollment}")
    private String enrollmentServiceUri;
    @Value("${service.uri.notification}")
    private String notificationServiceUri;
    @Value("${service.uri.admin}")
    private String adminServiceUri;

    @Bean
    public RouteLocator myRoutes(RouteLocatorBuilder builder) {
        return builder.routes()
                .route("faculty-service", p -> p
                        .path("/faculty/**")
                        .filters(f -> f
                                .rewritePath("\\/faculty", "/api/v1/faculty")
                                .filter(authJwtGatewayFilter.apply(new AuthJwtGatewayFilter.Config())
                        ))
                        .uri(facultyServiceUri))
                .route("discipline-service", p -> p
                        .path("/discipline/**")
                        .filters(f -> f
                                .rewritePath("\\/discipline", "/api/v1/discipline")
                                .filter(authJwtGatewayFilter.apply(new AuthJwtGatewayFilter.Config())
                        ))
                        .uri(disciplineServiceUri))
                .route("schedule-service", p -> p
                        .path("/schedule/**")
                        .filters(f -> f
                                .rewritePath("\\/schedule", "/api/v1/schedule")
                                .filter(authJwtGatewayFilter.apply(new AuthJwtGatewayFilter.Config())
                        ))
                        .uri(scheduleServiceUri))
                .route("auth-service", p -> p
                        .path("/auth/**")
                        .filters(f -> f
                                .rewritePath("\\/auth", "/api/v1/auth"))
                        .uri(authServiceUri))
                .route("user-service", p -> p
                        .path("/user/**")
                        .filters(f -> f
                                .rewritePath("\\/user", "/api/v1/user"))
                        .uri(userServiceUri))
                .route("enrollment-service", p -> p
                        .path("/enrollment/**")
                        .filters(f -> f
                                .rewritePath("\\/enrollment", "/api/v1/enrollment"))
                        .uri(enrollmentServiceUri))
                .route("notification-service", p -> p
                        .path("/notification/**")
                        .filters(f -> f
                                .rewritePath("\\/notification", "/notification"))
                        .uri(notificationServiceUri))
                .route("admin-service", p -> p
                        .path("/admin/**")
                        .filters(f -> f
                                .rewritePath("\\/admin", "/admin"))
                        .uri(adminServiceUri))
                .build();
    }
}
