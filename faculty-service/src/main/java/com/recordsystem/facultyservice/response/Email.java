package com.recordsystem.facultyservice.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Email {

    private String to;
    private String subject;
}
