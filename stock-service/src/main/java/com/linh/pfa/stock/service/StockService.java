package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linh.pfa.stock.entity.Portfolio;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.Stock;
import com.linh.pfa.stock.entity.StockRepository;
import com.linh.pfa.stock.util.StockPriceRetrieverChain;

@Service
public class StockService {
	@Autowired
	private StockRepository stockRespository;
	@Autowired
	private PortfolioRepository portfolioRespository;
	@Autowired
	private StockPriceRetrieverChain stockPriceRetrieverChain;

	@Transactional
    public void refreshPrice() {
		// refresh active portfolio stock price
		List<Portfolio> portfolios = portfolioRespository.findActivePortfolio();
		for (Portfolio portfolio : portfolios) {
			refreshPrice(portfolio.getStock());
		}
	}
	
	@Transactional
    public void refreshPrice(Stock stock) {
		if (stock.getCode()==null || stock.getCode().endsWith(".UN")) {
			return;
		}
		
		if (stock.getLatestPrice() != null && stock.getLatestPrice().doubleValue()>0
				&& Duration.between(stock.getUpdatedDate(), LocalDateTime.now()).toMinutes()<30) {
			// don't refresh if just refreshed
			return;
		}
		
        // Get current price and update to stock table
		double price = stockPriceRetrieverChain.build().getPrice(stock);
		if (price > 0) {
			stock.setLatestPrice(new BigDecimal(price));
	        stockRespository.save(stock);
		}
    }
}
