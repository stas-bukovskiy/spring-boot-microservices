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

    @JmsListener(destination = "${activemq.faculty.delete}")
    public void deletedFaculty(String message) {
        log.info(message);
    }

    private void makeSomeAnalytic(String data) {
        log.info(String.format("Performing analytic for: %s", data));
    }

    @JmsListener(destination = "${activemq.faculty.get}", selector = "filterCriteria = 'not-important'")
    public void getUnImportantMessages(String message) {
        log.info(message);
    }
}
