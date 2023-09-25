package com.recordsystem.service;

import com.recordsystem.exception.UserNotFoundException;
import com.recordsystem.response.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final RestTemplate restTemplate;

    @Value("${service.auth.base-url}")
    private String authServiceBaseUrl;

    @Retryable(
            retryFor = { HttpClientErrorException.class, UserNotFoundException.class }, // Specify the exceptions to retry
            backoff = @Backoff(delay = 1000, multiplier = 2) // Backoff settings (1s initial delay, doubling)
    )
    @Override
    public UserResponse authenticate(String jwt) {
        String endpoint = "/verify/{jwt}";

        // Build the URL with the userId parameter
        var uriBuilder = UriComponentsBuilder
                .fromUriString(authServiceBaseUrl)
                .path(endpoint)
                .buildAndExpand(jwt);

        // Send a GET request to the authentication service
        ResponseEntity<UserResponse> responseEntity = restTemplate.getForEntity(uriBuilder.toUriString(), UserResponse.class);

        // Check if the response is successful (HTTP status code 200)
        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        } else {
            throw new UserNotFoundException();
        }
    }
}
