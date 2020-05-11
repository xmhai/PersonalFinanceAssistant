package com.linh.pfa.account.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AssetController {
	@GetMapping("/asset/allocation")
	public List getAllocation() {
		List result = new ArrayList<>();
		addCategory(result, "CASH", (double) 50000);
		addCategory(result, "BONDS", (double) 50000);
		addCategory(result, "REITS", (double) 500000);
		addCategory(result, "STOCKS", (double) 500000);
		return result;
	}
	
	private void addCategory(List result, String category, double amt) {
		Map<String, Object> cash = new HashMap<String, Object>();
		cash.put("category", category);
		cash.put("amount", amt);
		result.add(cash);
	}
}
