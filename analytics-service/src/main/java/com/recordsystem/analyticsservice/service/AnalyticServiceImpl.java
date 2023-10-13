package com.recordsystem.analyticsservice.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class AnalyticServiceImpl implements AnalyticService {

    @JmsListener(destination = "${activemq.faculty.queue.name}")
    public void getFromFaculty(String message) {
        makeSomeAnalytic(message);
    }

    public void makeSomeAnalytic(String data) {
        log.info(String.format("Performing analytic for: %s", data));
    }

}
