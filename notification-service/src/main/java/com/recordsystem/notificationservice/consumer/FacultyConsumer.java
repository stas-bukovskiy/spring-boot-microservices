package com.recordsystem.notificationservice.consumer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class FacultyConsumer {

    private static final Logger log = LoggerFactory.getLogger(FacultyConsumer.class);

    @RabbitListener(queues = "${rabbitmq.faculty.queue.name}")
    public void consume(String message) {
        log.info(String.format("Received message: %s", message));
    }
}
