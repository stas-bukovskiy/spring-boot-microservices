package com.recordsystem.facultyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jms.annotation.EnableJms;

@SpringBootApplication
@EnableJms
public class FacultyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacultyServiceApplication.class, args);
	}

}
