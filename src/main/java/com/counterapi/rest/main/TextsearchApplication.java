package com.counterapi.rest.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.counterapi.rest.controller", "com.counterapi.rest.security"})
public class TextsearchApplication {

	public static void main(String[] args) {
		SpringApplication.run(TextsearchApplication.class, args);
	}

}
