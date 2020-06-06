package com.linh.pfa.account.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linh.pfa.account.service.AssetService;

@RestController
@RequestMapping("/assets")
public class AssetController {
	@Autowired
	private AssetService assetService;
	
	@GetMapping("/allocation")
	public List<Map<String, Object>> getAllocation() throws Exception {
		return assetService.getAllocation();
	}
	
	@GetMapping("/history")
	public List getHistories() throws Exception {
		return assetService.getHistories();
	}

	@PostMapping("/history")
	public ResponseEntity<Object> saveHistory() throws Exception {
		assetService.saveHistory();
        return ResponseEntity.ok().build();
	}
}
