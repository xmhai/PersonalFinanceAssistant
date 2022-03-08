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
import com.linh.pfa.stock.entity.PortfolioEntity;
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
	public ResponseEntity<List<PortfolioEntity>> getPortfolios() {
		return ResponseEntity.ok(portfolioRespository.findActivePortfolio());
	}

    @GetMapping("/{id}")
    public ResponseEntity<PortfolioEntity> findById(@PathVariable Long id) {
    	PortfolioEntity portfolio = portfolioRespository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(portfolio);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<PortfolioEntity> create(@RequestBody PortfolioEntity portfolio) {
		return ResponseEntity.ok(portfolioRespository.save(portfolio));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<PortfolioEntity> update(@PathVariable Long id, @RequestBody PortfolioEntity portfolio) {
        if (portfolioRespository.findById(id).orElse(null) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(portfolioRespository.save(portfolio));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	PortfolioEntity portfolio = portfolioRespository.findById(id).orElse(null);
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
