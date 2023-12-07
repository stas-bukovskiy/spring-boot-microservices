package com.recordsystem.gatewayservice.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@Component
public class AuthJwtGatewayFilter extends AbstractGatewayFilterFactory<AuthJwtGatewayFilter.Config> {

    private final WebClient webClient;

    @Autowired
    public AuthJwtGatewayFilter(WebClient webClient) {
        super(Config.class);
        this.webClient = webClient.mutate().baseUrl("http://localhost:8080/auth").build();
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String jwtToken = extractJwtToken(exchange.getRequest().getHeaders());

            if (jwtToken != null) {
                return verifyJwtToken(jwtToken)
                        .publishOn(Schedulers.boundedElastic())
                        .flatMap(response -> {
                            if (response.statusCode().is2xxSuccessful()) {
                                exchange.getRequest().mutate()
                                        .header("userId", response.bodyToMono(String.class).block())
                                        .build();
                                return chain.filter(exchange);
                            } else {
                                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                                return exchange.getResponse().setComplete();
                            }
                        });
            } else {
                exchange.getResponse().setStatusCode(HttpStatus.BAD_REQUEST);
                return exchange.getResponse().setComplete();
            }
        };
    }

    private String extractJwtToken(HttpHeaders headers) {
        String authorizationHeader = headers.getFirst(HttpHeaders.AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            return authorizationHeader.substring(7);
        }
        return null;
    }

    private Mono<ClientResponse> verifyJwtToken(String jwtToken) {
        return webClient.get()
                .uri("/verify/{jwtToken}", jwtToken)
                .exchange();
    }

    public static class Config {

    }
}
