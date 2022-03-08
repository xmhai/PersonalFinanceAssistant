package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.linh.pfa.stock.entity.PortfolioEntity;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.StockEntity;
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
	public StockEntity create(StockEntity stock) {
		stock = stockRespository.saveAndFlush(stock);
		stock = refreshPrice(stock);
		return stock;
	}
	
	@Transactional
	public StockEntity update(StockEntity stockOld, StockEntity stockNew) {
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
		List<PortfolioEntity> portfolios = portfolioRespository.findActivePortfolio();
		for (PortfolioEntity portfolio : portfolios) {
			refreshPrice(portfolio.getStock());
		}
	}
	
	@Transactional
    public StockEntity refreshPrice(StockEntity stock) {
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
	
	public BigDecimal getLatestPrice(StockEntity stock) {
		if (stock.getCode().endsWith(".UN")) {
			// psudeo stock
			return stock.getLatestPrice();
		}
		
		if (stock.getLatestPrice() != null && stock.getLatestPrice().doubleValue()>0
				&& Duration.between(stock.getUpdatedDate(), LocalDateTime.now()).toMinutes()<30) {
			// don't refresh if just refreshed
			return stock.getLatestPrice();
		}
		
        // Get current price and update to stock table
		return new BigDecimal(stockPriceRetrieverChain.getPrice(stock));
	}
}
