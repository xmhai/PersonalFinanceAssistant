package com.linh.pfa.job.stock;

import java.time.LocalDateTime;

import org.springframework.stereotype.Component;

@Component
public class PriceUpdateService {

	public void execute() {
		System.out.println(LocalDateTime.now().toString()+": Job is running...");
	}

}
