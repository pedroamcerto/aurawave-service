package com.aurawave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class AurawaveServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(AurawaveServiceApplication.class, args);
	}
}
