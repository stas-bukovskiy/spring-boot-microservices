package com.recordsystem.notificationservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class FacultyNotificationService {

    private static final Logger log = LoggerFactory.getLogger(FacultyNotificationService.class);

    @RabbitListener(queues = "${rabbitmq.faculty.queue.name}")
    public void receiveNewFaculty(String message) {
        log.info(String.format("Received message: %s", message));
    }
}
