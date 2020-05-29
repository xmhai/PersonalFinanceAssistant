package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.common.base.BusinessException;
import com.linh.pfa.common.enums.Category;
import com.linh.pfa.config.service.CurrencyService;
import com.linh.pfa.stock.entity.Portfolio;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.Profit;
import com.linh.pfa.stock.entity.ProfitRepository;
import com.linh.pfa.stock.entity.Stock;
import com.linh.pfa.stock.entity.StockRepository;

@Service
public class PortfolioService {
	@Autowired
	private PortfolioRepository portfolioRespository;
	@Autowired
	private ProfitRepository profitRespository;
	@Autowired
	private StockRepository stockRespository;
	@Autowired
	private CurrencyService currencyService;

	@Transactional
	public Portfolio addPosition(Long stockId, Integer quantity, BigDecimal price) {
    	Portfolio portfolio = null;
    	List<Portfolio> portfolios = portfolioRespository.findByStockId(stockId);
    	
    	Stock stock = stockRespository.findById(stockId).orElse(null);
    	
		// BUY
    	if (portfolios.isEmpty()) {
    		// create new portfolio
    		portfolio = new Portfolio(stock, quantity, price);
    	} else {
    		// update portfolio
    		portfolio = portfolios.get(0);
    		portfolio.add(quantity, price);
    	}
		return portfolioRespository.save(portfolio);
	}

	@Transactional
	public Portfolio reducePosition(Long stockId, Integer quantity, BigDecimal price) throws BusinessException {
    	Portfolio portfolio = null;
    	List<Portfolio> portfolios = portfolioRespository.findByStockId(stockId);
		// SELL, decrease portfolio
    	if (portfolios.isEmpty()) {
    		throw new BusinessException("No position, cannot sell the stock: "+stockId);
    	} else {
    		portfolio = portfolios.get(0);
    		if (portfolio.getQuantity() < quantity) {
	    		throw new BusinessException("No enough position, cannot sell this stock: "+stockId);
    		} else if (portfolio.getQuantity().equals(quantity)) {
    			portfolio = closePosition(portfolio, stockId, quantity, price);
    		} else {
	    		portfolio.reduce(quantity, price);
    		}
    	}
		return portfolioRespository.save(portfolio);
	}
	
	private Portfolio closePosition(Portfolio portfolio, Long stockId, Integer quantity, BigDecimal price) {
		// update realized profit
    	Profit profit = null;
    	List<Profit> profits = profitRespository.findByStockId(stockId);
    	if (profits.isEmpty()) {
    		// create new portfolio
    		profit = new Profit(stockId);
    	} else {
    		// update portfolio
    		profit = profits.get(0);
    	}
		BigDecimal realized = price.subtract(portfolio.getCost()).multiply(new BigDecimal(quantity));
		profit.setRealized(profit.getRealized().add(realized));
    	profitRespository.save(profit);
		
		// delete this portfolio
    	portfolio.setRealizedPrice(price);
		portfolio.setIsDeleted(true);
		return portfolio;
	}

	public Map<String, Double> getAllocation() {
		Map<Long, BigDecimal> currencies = currencyService.getExchangeRate();
		
		List<Portfolio> portfolios = portfolioRespository.findAll();
		double stockAmt = 0; 
		double bondAmt = 0; 
		double reitAmt = 0; 
		for (Portfolio portfolio : portfolios) {
			BigDecimal amt = portfolio.getStock().getLatestPrice().multiply(new BigDecimal(portfolio.getQuantity()));
			Long currencyId = Long.valueOf(portfolio.getStock().getCurrency().getValue());
			double amtSGD = amt.doubleValue() * currencies.get(currencyId).doubleValue();
			if (portfolio.getStock().getCategory()==Category.STOCKS) {
				stockAmt = stockAmt + amtSGD;
			} else if (portfolio.getStock().getCategory()==Category.BONDS) {
				bondAmt = bondAmt + amtSGD;
			} else if (portfolio.getStock().getCategory()==Category.REITS) {
				reitAmt = reitAmt + amtSGD;
			}
		}
		
		Map<String, Double> allocation = new HashMap<String, Double>();
		allocation.put(Category.STOCKS.name(), stockAmt);
		allocation.put(Category.BONDS.name(), bondAmt);
		allocation.put(Category.REITS.name(), reitAmt);
		return allocation;
	}
}
