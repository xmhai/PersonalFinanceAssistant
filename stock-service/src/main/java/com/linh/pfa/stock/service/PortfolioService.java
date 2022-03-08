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
import com.linh.pfa.stock.entity.PortfolioEntity;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.StockEntity;
import com.linh.pfa.stock.entity.StockRepository;

@Service
public class PortfolioService {
	@Autowired
	private PortfolioRepository portfolioRespository;
	@Autowired
	private ProfitService profitService;
	@Autowired
	private StockRepository stockRespository;
	@Autowired
	private CommonService commonService;

	@Transactional
	public PortfolioEntity addPosition(Long stockId, Integer quantity, BigDecimal price) {
    	PortfolioEntity portfolio = null;
    	List<PortfolioEntity> portfolios = portfolioRespository.findByStockId(stockId);
    	
    	StockEntity stock = stockRespository.findById(stockId).orElse(null);
    	
    	// create new profit item if not exists
    	profitService.addStock(stock);
    	
		// BUY
    	if (portfolios.isEmpty()) {
    		// create new portfolio
    		portfolio = new PortfolioEntity(stock, quantity, price);
    	} else {
    		// update portfolio
    		portfolio = portfolios.get(0);
    		portfolio.add(quantity, price);
    	}
		return portfolioRespository.save(portfolio);
	}

	@Transactional
	public PortfolioEntity reducePosition(Long stockId, Integer quantity, BigDecimal price) throws BusinessException {
    	PortfolioEntity portfolio = null;
    	List<PortfolioEntity> portfolios = portfolioRespository.findByStockId(stockId);
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
	
	private PortfolioEntity closePosition(PortfolioEntity portfolio, Long stockId, Integer quantity, BigDecimal price) {
		Map<Long, BigDecimal> currencies = commonService.getExchangeRate();
    	StockEntity stock = stockRespository.findById(stockId).orElse(null);
		
    	// calculate profit
		Long currencyId = Long.valueOf(stock.getCurrency().getValue());
		BigDecimal realized = price.subtract(portfolio.getCost()).multiply(new BigDecimal(quantity)).multiply(currencies.get(currencyId));
    	
		// update realized profit
		profitService.addRealized(stock, realized);
		
		// close portfolio
    	portfolio.close(price);
		return portfolio;
	}

	public Map<String, Double> getAllocation() {
		Map<Long, BigDecimal> currencies = commonService.getExchangeRate();
		
		List<PortfolioEntity> portfolios = portfolioRespository.findActivePortfolio();
		double stockAmt = 0; 
		double bondAmt = 0; 
		double reitAmt = 0; 
		for (PortfolioEntity portfolio : portfolios) {
			StockEntity stock = portfolio.getStock();
			BigDecimal amt = stock.getLatestPrice().multiply(new BigDecimal(portfolio.getQuantity()));
			Long currencyId = Long.valueOf(stock.getCurrency().getValue());
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
