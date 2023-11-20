package com.recordsystem;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableAdminServer
@SpringBootApplication
public class AdminPanelApplication {
    public static void main(String[] args) {
        SpringApplication.run(AdminPanelApplication.class, args);
    }
}
