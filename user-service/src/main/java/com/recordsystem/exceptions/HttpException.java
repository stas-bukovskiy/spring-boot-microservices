package com.recordsystem.exceptions;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@EqualsAndHashCode(callSuper = true)
@Data
public class HttpException extends RuntimeException{

    private final int status;

    public HttpException(String message, int status) {
        super(message);
        this.status = status;
    }
}
