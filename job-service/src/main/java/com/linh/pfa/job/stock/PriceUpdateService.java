package com.linh.pfa.job.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.linh.pfa.job.config.JobLog;
import com.linh.pfa.job.config.JobLogRepository;

@Component
public class PriceUpdateService {
	@Autowired
	private RestTemplate restTemplate;

	public void execute() {
		// retrieve stocks allocation
		ResponseEntity response = restTemplate.exchange("http://localhost:10082/stocks/refresh/price", HttpMethod.PUT, null, Void.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			// log job success
		} else {
			// log job failure
		}
	}
}
