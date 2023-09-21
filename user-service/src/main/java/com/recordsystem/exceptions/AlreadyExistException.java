package com.recordsystem.exceptions;

public class AlreadyExistException extends HttpException {
    public AlreadyExistException(String message) {
        super(message, 409);
    }
}
