package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.common.base.BusinessException;
import com.linh.pfa.stock.entity.Portfolio;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.Profit;
import com.linh.pfa.stock.entity.ProfitRepository;

@Service
public class PortfolioService {
	@Autowired
	private PortfolioRepository portfolioRespository;
	@Autowired
	private ProfitRepository profitRespository;

	@Transactional
	public Portfolio addPosition(Long stockId, Integer quantity, BigDecimal price) {
    	Portfolio portfolio = null;
    	List<Portfolio> portfolios = portfolioRespository.findByStockId(stockId);
		// BUY
    	if (portfolios.isEmpty()) {
    		// create new portfolio
    		portfolio = new Portfolio(stockId, quantity, price);
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
		portfolio.setIsDeleted(true);
		return portfolio;
	}
}
