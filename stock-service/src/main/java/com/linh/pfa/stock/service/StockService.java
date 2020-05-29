package com.linh.pfa.stock.service;

import java.math.BigDecimal;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.linh.pfa.common.enums.Exchange;
import com.linh.pfa.stock.entity.Stock;
import com.linh.pfa.stock.entity.StockRepository;

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
		if (code.indexOf('.')<0) {
			if (stock.getExchange()==Exchange.SGX) {
				code = code + ".SI";
			} else if (stock.getExchange()==Exchange.HKEX) {
				code = code + ".HK";
			}
		}
		
		String url = String.format("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=J8M5Z01DE30ZSGAA", code);
		String response = restTemplate.getForObject(url, String.class);
		String s = JsonPath.read(response, "$.['Global Quote'].['05. price']");
		
		return Double.valueOf(s);
	}
}
