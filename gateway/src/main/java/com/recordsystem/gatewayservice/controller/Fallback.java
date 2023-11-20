package com.recordsystem.gatewayservice.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Fallback {

    @GetMapping("/fallback")
    public ResponseEntity<String> fallbackResponse() {
        String fallbackMessage = "This is the fallback response";
        return ResponseEntity.status(HttpStatus.OK)
                .contentType(MediaType.APPLICATION_JSON)
                .body(fallbackMessage);
    }
}
