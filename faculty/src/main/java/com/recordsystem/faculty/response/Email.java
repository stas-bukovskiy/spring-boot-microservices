package com.recordsystem.faculty.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString
public class Email {

    private String to;
    private String subject;
}
