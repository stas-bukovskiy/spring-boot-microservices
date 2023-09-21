package com.recordsystem.userservice.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@Setter
@AllArgsConstructor
public class MessageResponse {
    private String message;
    private int status;

    public MessageResponse(int status, String message) {
        this.status = status;
        this.message = message;
    }

}
