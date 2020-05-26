package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.pfa.stock.entity.Dividend;
import com.linh.pfa.stock.entity.DividendRepository;
import com.linh.pfa.stock.entity.Profit;
import com.linh.pfa.stock.entity.ProfitRepository;

@Service
public class DividendService {
	@Autowired
	private DividendRepository dividendRespository;
	@Autowired
	private ProfitRepository profitRespository;
	
	@Transactional
	public Dividend create(Dividend dividend) {
		// update profit
    	Profit profit = null;
    	List<Profit> profits = profitRespository.findByStockId(dividend.getStockId());
    	if (profits.isEmpty()) {
    		// create new portfolio
    		profit = new Profit(dividend.getStockId());
    	} else {
    		// update portfolio
    		profit = profits.get(0);
    	}
		profit.setDividend(profit.getDividend().add(dividend.getAmount()));
    	profitRespository.save(profit);
		
		return dividendRespository.save(dividend);
	}

	@Transactional
	public Dividend update(Dividend dividendOld, Dividend dividendNew) {
		// update profit
		if (!dividendOld.getAmount().equals(dividendNew.getAmount())) {
			BigDecimal difference = dividendNew.getAmount().subtract(dividendOld.getAmount());
	    	Profit profit = profitRespository.findByStockId(dividendNew.getStockId()).get(0);
			profit.setDividend(profit.getDividend().add(difference));
	    	profitRespository.save(profit);
		}

		dividendNew.setCreatedBy(dividendOld.getCreatedBy());
		dividendNew.setCreatedDate(dividendOld.getCreatedDate());
		dividendNew.setIsDeleted(false);
    	
		return dividendRespository.save(dividendNew);
	}

	@Transactional
	public Dividend delete(Dividend dividend) {
		// update profit
    	Profit profit = profitRespository.findByStockId(dividend.getStockId()).get(0);
		profit.setDividend(profit.getDividend().subtract(dividend.getAmount()));
    	profitRespository.save(profit);
		
    	dividend.setIsDeleted(true);
		return dividendRespository.save(dividend);
	}
}
