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
	public Stock create(Stock stock) {
		stock = stockRespository.saveAndFlush(stock);
		stock = refreshPrice(stock);
		return stock;
	}
	
	@Transactional
	public Stock update(Stock stockOld, Stock stockNew) {
		boolean codeChanged = !stockOld.getCode().equals(stockNew.getCode()); 
        stockRespository.save(stockNew); 
		if (codeChanged) {
			refreshPrice(stockNew);
		}
		return stockNew;
	}
	
	@Transactional
    public void refreshPrice() {
		// refresh active portfolio stock price
		List<Portfolio> portfolios = portfolioRespository.findActivePortfolio();
		for (Portfolio portfolio : portfolios) {
			refreshPrice(portfolio.getStock());
		}
	}
	
	@Transactional
    public Stock refreshPrice(Stock stock) {
		if (stock.getCode()==null || stock.getCode().endsWith(".UN")) {
			return stock;
		}
		
		if (stock.getLatestPrice() != null && stock.getLatestPrice().doubleValue()>0
				&& Duration.between(stock.getUpdatedDate(), LocalDateTime.now()).toMinutes()<30) {
			// don't refresh if just refreshed
			return stock;
		}
		
        // Get current price and update to stock table
		double price = stockPriceRetrieverChain.getPrice(stock);
		if (price > 0) {
			stock.setLatestPrice(new BigDecimal(price));
	        stockRespository.save(stock);
		}
		
		return stock;
    }
}
