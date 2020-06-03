package com.linh.pfa.job;

import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

@SpringBootApplication
public class JobServiceApplication {

	@Autowired
	private ApplicationContext applicationContext;
	
	public static void main(String[] args) {
		SpringApplication.run(JobServiceApplication.class, args);
	}
	
	@PreDestroy
	public void stop() {
		System.out.println("job service is shutting down...");
	}
}
