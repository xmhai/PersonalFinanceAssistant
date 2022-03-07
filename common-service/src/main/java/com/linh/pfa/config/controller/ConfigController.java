package com.linh.pfa.config.controller;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linh.pfa.api.common.Action;
import com.linh.pfa.api.common.AssetCategory;
import com.linh.pfa.api.common.ConfigServiceApi;
import com.linh.pfa.api.common.Currency;
import com.linh.pfa.api.common.Exchange;
import com.linh.pfa.config.entity.ActionEntity;
import com.linh.pfa.config.entity.ActionRepository;
import com.linh.pfa.config.entity.AssetCategoryEntity;
import com.linh.pfa.config.entity.AssetCategoryRepository;
import com.linh.pfa.config.entity.CurrencyEntity;
import com.linh.pfa.config.entity.CurrencyRepository;
import com.linh.pfa.config.entity.ExchangeEntity;
import com.linh.pfa.config.entity.ExchangeRepository;

@RestController
@RequestMapping("/config")
public class ConfigController implements ConfigServiceApi {
	@Autowired
	private CurrencyRepository currencyRespository;
	@Autowired
	private AssetCategoryRepository categoryRespository;
	@Autowired
	private ExchangeRepository exchangeRespository;
	@Autowired
	private ActionRepository actionRespository;
	
	private ModelMapper mapper = new ModelMapper(); 
	
	@GetMapping("currencies")
	public ResponseEntity<List<Currency>> getCurrencies() {
		List<CurrencyEntity> l = currencyRespository.findAll();
		
		//List<Currency> currencies = new ArrayList<Currency>();
		//mapper.map(l, currencies);
		Type listType = new TypeToken<List<Currency>>(){}.getType();
		List<Currency> currencies = mapper.map(l, listType);
		
		
		return ResponseEntity.ok(currencies);
	}
	
	@GetMapping("categories")
	public ResponseEntity<List<AssetCategory>> getCategoris() {
		List<AssetCategoryEntity> l = categoryRespository.findAll();

		List<AssetCategory> categories = new ArrayList<AssetCategory>();
		mapper.map(l, categories);
		
		return ResponseEntity.ok(categories);
	}
	
	@GetMapping("exchanges")
	public ResponseEntity<List<Exchange>> getExchanges() {
		List<ExchangeEntity> l = exchangeRespository.findAll();

		List<Exchange> exchanges = new ArrayList<Exchange>();
		mapper.map(l, exchanges);
		
		return ResponseEntity.ok(exchanges);
	}
	
	@GetMapping("actions")
	public ResponseEntity<List<Action>> getTransactionTypes() {
		List<ActionEntity> l = actionRespository.findAll();

		List<Action> actions = new ArrayList<Action>();
		mapper.map(l, actions);
		
		return ResponseEntity.ok(actions);
	}
}
