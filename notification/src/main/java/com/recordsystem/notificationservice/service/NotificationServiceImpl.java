package com.recordsystem.notificationservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    @Override
    public void sendNotification(String notificationText) {
        log.info(String.format("Sending notification: %s", notificationText));
    }
}
