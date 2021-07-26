package com.guestbook.guestapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
public class GuestappApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestappApplication.class, args);
	}

}
