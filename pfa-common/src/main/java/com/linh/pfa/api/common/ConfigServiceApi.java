package com.linh.pfa.api.common;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/config")
public interface ConfigServiceApi {
	@GetMapping(value = "/currencies", produces = "application/json")
	ResponseEntity<List<Currency>> getCurrencies();

	@GetMapping(value = "/categories", produces = "application/json")
	public ResponseEntity<List<AssetCategory>> getCategoris();
	
	@GetMapping(value = "/exchanges", produces = "application/json")
	public ResponseEntity<List<Exchange>> getExchanges();

	@GetMapping(value = "/actions", produces = "application/json")
	public ResponseEntity<List<Action>> getTransactionTypes();
}
