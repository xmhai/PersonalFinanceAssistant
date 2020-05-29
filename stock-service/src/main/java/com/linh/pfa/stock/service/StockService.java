package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.jayway.jsonpath.JsonPath;
import com.linh.pfa.common.enums.Exchange;
import com.linh.pfa.stock.entity.Portfolio;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.Stock;
import com.linh.pfa.stock.entity.StockRepository;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRespository;
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private PortfolioRepository portfolioRespository;

	@Transactional
    public void refreshPrice() {
		// refresh active portfolio stock price
		List<Portfolio> portfolios = portfolioRespository.findActivePortfolio();
		for (Portfolio portfolio : portfolios) {
			refreshPrice(portfolio.getStock());
			try {
				// Thank you for using Alpha Vantage! Our standard API call frequency is 5 calls per minute
				Thread.sleep(15000);
			} catch (InterruptedException e) { ; }
		}
	}
	
	@Transactional
    public void refreshPrice(Stock stock) {
		if (stock.getCode()==null || stock.getCode().endsWith(".UN")) {
			return;
		}
		
        // Get current price and update to stock table
		double price = getPriceFromAlphavantage(stock);
		if (price > 0) {
			stock.setLatestPrice(new BigDecimal(price));
	        stockRespository.save(stock);
		}
    }
	
	private double getPriceFromAlphavantage(Stock stock) {
		String code = stock.getCode();
		
		if (code.endsWith(".UN")) {
			return 0;
		}
		
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
			response = restTemplate.getForObject(url, String.class);
			s = JsonPath.read(response, "$.['Global Quote'].['05. price']");
			return Double.valueOf(s);
		} catch (Exception e) {
			System.out.println(response);
			System.out.println(s);
			return 0;
		}
	}
	
	
}
