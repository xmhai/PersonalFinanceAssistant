package com.linh.pfa;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@ComponentScan(basePackages = "com.linh")
@Slf4j
public class CommonServiceApplication {
	public static void main(String[] args) {
		log.info("Application running...");
		SpringApplication.run(CommonServiceApplication.class, args);
	}
}
