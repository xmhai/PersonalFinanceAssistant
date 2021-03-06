package com.linh.pfa.config.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linh.pfa.config.entity.Action;
import com.linh.pfa.config.entity.ActionRepository;
import com.linh.pfa.config.entity.AssetCategory;
import com.linh.pfa.config.entity.AssetCategoryRepository;
import com.linh.pfa.config.entity.Currency;
import com.linh.pfa.config.entity.CurrencyRepository;
import com.linh.pfa.config.entity.Exchange;
import com.linh.pfa.config.entity.ExchangeRepository;

@RestController
@RequestMapping("/config")
public class ConfigController {
	@Autowired
	private CurrencyRepository currencyRespository;
	@Autowired
	private AssetCategoryRepository categoryRespository;
	@Autowired
	private ExchangeRepository exchangeRespository;
	@Autowired
	private ActionRepository actionRespository;
	
	@GetMapping("currencies")
	public ResponseEntity<List<Currency>> getCurrencies() {
		return ResponseEntity.ok(currencyRespository.findAll());
	}
	
	@GetMapping("categories")
	public ResponseEntity<List<AssetCategory>> getCategoris() {
		return ResponseEntity.ok(categoryRespository.findAll());
	}
	
	@GetMapping("exchanges")
	public ResponseEntity<List<Exchange>> getExchanges() {
		return ResponseEntity.ok(exchangeRespository.findAll());
	}
	
	@GetMapping("actions")
	public ResponseEntity<List<Action>> getTransactionTypes() {
		return ResponseEntity.ok(actionRespository.findAll());
	}
}
