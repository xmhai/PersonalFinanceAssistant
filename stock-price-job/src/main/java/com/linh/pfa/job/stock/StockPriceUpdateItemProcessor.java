package com.linh.pfa.job.stock;

import java.math.BigDecimal;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

public class StockPriceUpdateItemProcessor implements ItemProcessor<StockPriceUpdate, StockPriceUpdate> {
	@Autowired
	private RestTemplate restTemplate;

    @Value("${pfa.endpoint.stock-service}")
    private String stockServiceEndpoint;
	
	@Override
	public StockPriceUpdate process(StockPriceUpdate item) {
		// get latest stock price from stock service
		BigDecimal price = getLastPrice(item.getId());
		item.setLatestPrice(price);
		return item;
	}
	
	private BigDecimal getLastPrice(Long stockId) {
		// retrieve stocks allocation
		ResponseEntity<String> response = restTemplate.exchange(stockServiceEndpoint + "/stocks/latestprice/"+stockId, HttpMethod.GET, null, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			BigDecimal price = new BigDecimal(response.getBody()).setScale(3, BigDecimal.ROUND_HALF_UP);
			return price;
		} else {
			return new BigDecimal(0);
		}
	}
}
