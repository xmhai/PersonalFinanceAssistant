package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.pfa.config.service.CurrencyService;
import com.linh.pfa.stock.entity.Portfolio;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.Profit;
import com.linh.pfa.stock.entity.ProfitRepository;
import com.linh.pfa.stock.entity.Stock;
import com.linh.pfa.stock.entity.StockRepository;

@Service
public class ProfitService {
	@Autowired
	private ProfitRepository profitRespository;
	@Autowired
	private PortfolioRepository portfolioRespository;
	@Autowired
	private StockRepository stockRepository;
	@Autowired
	private CurrencyService currencyService;
	
	
	public List<Map<String, Object>> getProfits() {
		Map<Long, BigDecimal> currencies = currencyService.getExchangeRate();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		List<Profit> profits = profitRespository.findAll();
		for (Profit profit : profits) {
    		Stock stock = profit.getStock();
			Long currencyId = Long.valueOf(stock.getCurrency().getValue());
			
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("id", profit.getId());
			map.put("stock", stock);
			BigDecimal realizedSGD = profit.getRealized().multiply(currencies.get(currencyId)); // realized is in the stock currency
			map.put("realized", realizedSGD); 
			map.put("dividend", profit.getDividend());
			
			// get unrealized
			BigDecimal unrealizedSGD = new BigDecimal(0.00);
			List<Portfolio> portfolios = portfolioRespository.findByStockId(stock.getId());
	    	if (!portfolios.isEmpty()) {
	    		Portfolio p = portfolios.get(0);
	    		unrealizedSGD = stock.getLatestPrice().subtract(p.getCost()).multiply(new BigDecimal(p.getQuantity())).multiply(currencies.get(currencyId)); 
	    	}
			map.put("unrealized", unrealizedSGD);
			
	    	// get total
			map.put("profit", profit.getDividend().add(realizedSGD).add(unrealizedSGD));
	    	
			result.add(map);
		}
		return result;
	}
}
