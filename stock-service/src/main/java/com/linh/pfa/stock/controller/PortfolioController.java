package com.linh.pfa.stock.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linh.pfa.common.enums.Category;
import com.linh.pfa.stock.entity.Portfolio;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.service.PortfolioService;

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
	@Autowired
	private PortfolioService portfolioService;
	@Autowired
	private PortfolioRepository portfolioRespository;
	
	@GetMapping("")
	public ResponseEntity<List<Portfolio>> getPortfolios() {
		return ResponseEntity.ok(portfolioRespository.findActivePortfolio());
	}

    @GetMapping("/{id}")
    public ResponseEntity<Portfolio> findById(@PathVariable Long id) {
    	Portfolio portfolio = portfolioRespository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(portfolio);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<Portfolio> create(@RequestBody Portfolio portfolio) {
		return ResponseEntity.ok(portfolioRespository.save(portfolio));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Portfolio> update(@PathVariable Long id, @RequestBody Portfolio portfolio) {
        if (portfolioRespository.findById(id).orElse(null) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(portfolioRespository.save(portfolio));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Portfolio portfolio = portfolioRespository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.badRequest().build();
        }

        portfolioRespository.delete(portfolio);
        return ResponseEntity.ok().build();
    }

	@GetMapping("/allocation")
	public ResponseEntity<Map<String, Double>> getAllocation() {
		return ResponseEntity.ok(portfolioService.getAllocation());
	}

}
