package com.recordsystem.userservice.controller;

import com.recordsystem.exceptions.ErrorHttpResponseDto;
import com.recordsystem.exceptions.HttpException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@Order(2)
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {HttpException.class})
    public ResponseEntity<ErrorHttpResponseDto> handleRuntimeException(HttpException ex) {
        var errorHttpResponseDto = new ErrorHttpResponseDto(ex.getStatus(), ex.getMessage(), LocalDateTime.now());
        log.error("HttpException: ", ex);
        return ResponseEntity.status(ex.getStatus())
                .contentType(MediaType.APPLICATION_JSON)
                .body(errorHttpResponseDto);
    }
}