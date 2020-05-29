package com.linh.pfa.config.service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.pfa.config.entity.Currency;
import com.linh.pfa.config.entity.CurrencyRepository;

@Service
public class CurrencyService {
	@Autowired
	private CurrencyRepository currencyRespository;
	
	public Map<Long, BigDecimal> getExchangeRate() {
		Map<Long, BigDecimal> map = new HashMap<Long, BigDecimal>();
		List<Currency> currencies = currencyRespository.findAll();
		for (Currency currency : currencies) {
			map.put(currency.getId(), currency.getExchangeRate());
		}
		return map;
	}
}
