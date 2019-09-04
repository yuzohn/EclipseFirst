package com.mail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MessageMailProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(MessageMailProjectApplication.class, args);
	}

}
