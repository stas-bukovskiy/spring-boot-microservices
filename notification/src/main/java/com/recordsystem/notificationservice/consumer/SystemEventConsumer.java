package com.recordsystem.notificationservice.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class SystemEventConsumer {

    @JmsListener(destination = "system_events")
    private void handleSystemEvent(String event) {
        log.info(event);
    }
}
