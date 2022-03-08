package com.linh.pfa.stock.service;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.pfa.stock.entity.DividendEntity;
import com.linh.pfa.stock.entity.DividendRepository;
import com.linh.pfa.stock.entity.ProfitEntity;
import com.linh.pfa.stock.entity.ProfitRepository;

@Service
public class DividendService {
	@Autowired
	private DividendRepository dividendRespository;
	@Autowired
	private ProfitRepository profitRespository;
	
	@Transactional
	public DividendEntity create(DividendEntity dividend) {
		// update profit
    	ProfitEntity profit = null;
    	List<ProfitEntity> profits = profitRespository.findByStockId(dividend.getStockId());
		profit = profits.get(0);
		profit.setDividend(profit.getDividend().add(dividend.getAmount()));
    	profitRespository.save(profit);
		
		return dividendRespository.save(dividend);
	}

	@Transactional
	public DividendEntity update(DividendEntity dividendOld, DividendEntity dividendNew) {
		// update profit
		if (!dividendOld.getAmount().equals(dividendNew.getAmount())) {
			BigDecimal difference = dividendNew.getAmount().subtract(dividendOld.getAmount());
	    	ProfitEntity profit = profitRespository.findByStockId(dividendNew.getStockId()).get(0);
			profit.setDividend(profit.getDividend().add(difference));
	    	profitRespository.save(profit);
		}

		return dividendRespository.save(dividendNew);
	}

	@Transactional
	public void delete(DividendEntity dividend) {
		// update profit
    	ProfitEntity profit = profitRespository.findByStockId(dividend.getStockId()).get(0);
		profit.setDividend(profit.getDividend().subtract(dividend.getAmount()));
    	profitRespository.save(profit);
		
		dividendRespository.delete(dividend);
	}
}
