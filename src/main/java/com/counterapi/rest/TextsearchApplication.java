package com.counterapi.rest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication
public class TextsearchApplication {
	public static void main(String[] args) {
		SpringApplication.run(TextsearchApplication.class, args);
		log.info("Started TextsearchApplication...");
	}
}
