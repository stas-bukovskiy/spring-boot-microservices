package com.recordsystem.facultyservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"com.recordsystem.facultyservice"})
public class FacultyServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(FacultyServiceApplication.class, args);
	}

}
