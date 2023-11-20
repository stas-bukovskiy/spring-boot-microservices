package com.recordsystem.notificationservice.email;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Email {

    private String to;
    private String subject;
}
