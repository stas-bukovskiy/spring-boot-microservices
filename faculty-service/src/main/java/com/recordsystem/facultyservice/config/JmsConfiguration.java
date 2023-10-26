//package com.recordsystem.facultyservice.config;
//
//import jakarta.jms.Topic;
//import org.apache.activemq.command.ActiveMQTopic;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//@Configuration
//public class JmsConfiguration {
//
//    @Value("${activemq.faculty.queue.name}")
//    private String topicName;
//
//    @Bean
//    public Topic facultyCreatedTopic() {
//        return new ActiveMQTopic(topicName);
//    }
//}
