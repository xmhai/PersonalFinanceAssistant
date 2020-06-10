package com.linh.pfa.job.stock;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PriceUpdateService {
	@Autowired
	private RestTemplate restTemplate;

    @Value("${pfa.endpoint.stock-service}")
    private String stockServiceEndpoint;
	
	public void execute() {
		// retrieve stocks allocation
		ResponseEntity response = restTemplate.exchange(stockServiceEndpoint + "/stocks/refresh/price", HttpMethod.PUT, null, Void.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			// log job success
		} else {
			// log job failure
		}
	}
}
