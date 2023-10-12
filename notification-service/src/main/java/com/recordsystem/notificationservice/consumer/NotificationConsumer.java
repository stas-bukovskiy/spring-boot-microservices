package com.recordsystem.notificationservice.consumer;

import com.recordsystem.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class NotificationConsumer {

    private final NotificationService service;

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void receiveNewFaculty(String notificationText) {
        log.info(String.format("Received message with notification text %s", notificationText));

        service.sendNotification(notificationText);
    }
}
