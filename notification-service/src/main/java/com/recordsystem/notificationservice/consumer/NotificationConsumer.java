package com.recordsystem.notificationservice.consumer;

import com.recordsystem.notificationservice.email.Email;
import com.recordsystem.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService service;

    @JmsListener(destination = "${activemq.faculty.queue.name}")
    public void receiveNewFaculty(String email) {
        log.info("Received <" + email + ">");

        service.sendNotification(email);
    }
}
