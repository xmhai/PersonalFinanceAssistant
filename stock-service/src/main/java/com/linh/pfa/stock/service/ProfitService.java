package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.linh.pfa.stock.entity.PortfolioEntity;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.ProfitEntity;
import com.linh.pfa.stock.entity.ProfitRepository;
import com.linh.pfa.stock.entity.StockEntity;

@Service
public class ProfitService {
	@Autowired
	private ProfitRepository profitRespository;
	@Autowired
	private PortfolioRepository portfolioRespository;
	@Autowired
	private CommonService commonService;
	
	@Transactional
	public void addRealized(StockEntity stock, BigDecimal realized) {
    	ProfitEntity profit = null;
    	List<ProfitEntity> profits = profitRespository.findByStockId(stock.getId());
    	if (profits.isEmpty()) {
    		// create new portfolio
    		profit = new ProfitEntity(stock);
    	} else {
    		// update portfolio
    		profit = profits.get(0);
    	}
		profit.setRealized(profit.getRealized().add(realized));
    	profitRespository.save(profit);
	}
	
	public List<Map<String, Object>> getProfits() {
		// retrieve exchange rate
		Map<Long, BigDecimal> currencyMap = commonService.getExchangeRate();
		
		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<ProfitEntity> profits = profitRespository.findAll();
		for (ProfitEntity profit : profits) {
    		StockEntity stock = profit.getStock();
			Long currencyId = Long.valueOf(stock.getCurrency().getValue());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", profit.getId());
			map.put("stock", stock);
			map.put("realized", profit.getRealized()); 
			map.put("dividend", profit.getDividend());
			
			// get unrealized
			BigDecimal unrealizedSGD = new BigDecimal(0.00);
			List<PortfolioEntity> portfolios = portfolioRespository.findByStockId(stock.getId());
	    	if (!portfolios.isEmpty()) {
	    		PortfolioEntity p = portfolios.get(0);
	    		unrealizedSGD = stock.getLatestPrice().subtract(p.getCost()).multiply(new BigDecimal(p.getQuantity())).multiply(currencyMap.get(currencyId)); 
	    	}
			map.put("unrealized", unrealizedSGD);
			
	    	// get total
			map.put("profit", profit.getDividend().add(profit.getRealized()).add(unrealizedSGD));
	    	
			result.add(map);
		}
		return result;
	}

	@Transactional
	public void addStock(StockEntity stock) {
    	List<ProfitEntity> profits = profitRespository.findByStockId(stock.getId());
    	if (profits.isEmpty()) {
    		// create new portfolio
    		ProfitEntity profit = new ProfitEntity(stock);
        	profitRespository.save(profit);
    	}
	}
}
