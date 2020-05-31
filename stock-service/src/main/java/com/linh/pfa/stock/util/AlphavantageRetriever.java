package com.linh.pfa.stock.util;

import java.time.Duration;
import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.linh.pfa.common.enums.Exchange;
import com.linh.pfa.stock.entity.Stock;

@Service
public class AlphavantageRetriever implements StockPriceRetriever {
	@Autowired
	private RestTemplate restTemplate;
	
	private LocalDateTime invokeTime;
	
	@Override
	public double getPrice(Stock stock) {
		if (invokeTime != null && Duration.between(invokeTime, LocalDateTime.now()).getSeconds()<15) {
			try {
				// Thank you for using Alpha Vantage! Our standard API call frequency is 5 calls per minute
				Thread.sleep(15000);
			} catch (InterruptedException e) { ; }
		}

		String code = stock.getCode();
        // Format stock code 
		if (code.indexOf('.')<0) {
			if (stock.getExchange()==Exchange.SGX) {
				code = code + ".SI";
			} else if (stock.getExchange()==Exchange.HKEX) {
				code = code + ".HK";
			}
		}
		
		String url = String.format("https://www.alphavantage.co/query?function=GLOBAL_QUOTE&symbol=%s&apikey=J8M5Z01DE30ZSGAA", code);
		System.out.println(code);
		System.out.println(url);
		String response = null;
		String s = null;
		try {
			invokeTime = LocalDateTime.now();
			response = restTemplate.getForObject(url, String.class);
			s = JsonPath.read(response, "$.['Global Quote'].['05. price']");
			return Double.valueOf(s);
		} catch (Exception e) {
			System.out.println(response);
			return 0;
		}
	}
}
