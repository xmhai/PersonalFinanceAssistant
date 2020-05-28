package com.linh.pfa.config.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.linh.pfa.config.entity.Stock;
import com.linh.pfa.config.entity.StockRepository;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRespository;
	@Autowired
	private RestTemplate restTemplate;
	
	@Transactional
    public void refreshPrice(Stock stock) {
        // Get current price and update to stock table
		double price = getPriceFromAlphavantage(stock);
		stock.setLatestPrice(new BigDecimal(price));
        stockRespository.save(stock);
    }
	
	private double getPriceFromAlphavantage(Stock stock) {
        // Format stock code 
		String code = stock.getCode();
		if ("SGX".equals(stock.getExchange().getCode())) {
			code = code + ".SI";
		} else if ("HKEX".equals(stock.getExchange().getCode())) {
			code = code + ".SI";
		}
		
		String url = String.format("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=J8M5Z01DE30ZSGAA", code);
		String response = restTemplate.getForObject(url, String.class);
		String s = JsonPath.read(response, "$.['Global Quote'].['05. price']");
		
		return Double.valueOf(s);
	}
}
