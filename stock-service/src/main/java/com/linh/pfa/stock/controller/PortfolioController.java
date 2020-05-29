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

@RestController
@RequestMapping("/portfolios")
public class PortfolioController {
	@Autowired
	private PortfolioRepository portfolioRespository;
	
	@GetMapping("")
	public ResponseEntity<List<Portfolio>> getPortfolios() {
		return ResponseEntity.ok(portfolioRespository.findAll());
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
		return ResponseEntity.ok(portfolioRespository.saveAndFlush(portfolio));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Portfolio> update(@PathVariable Long id, @RequestBody Portfolio portfolio) {
    	Portfolio portfolioExisting = portfolioRespository.findById(id).orElse(null);
        if (!portfolioRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save original values 
        portfolio.setCreatedBy(portfolioExisting.getCreatedBy());
        portfolio.setCreatedDate(portfolioExisting.getCreatedDate());
        portfolio.setIsDeleted(false);
        
        return ResponseEntity.ok(portfolioRespository.saveAndFlush(portfolio));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Portfolio portfolio = portfolioRespository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.badRequest().build();
        }

        portfolioRespository.save(portfolio);
        return ResponseEntity.ok().build();
    }

	@GetMapping("/allocation")
	public ResponseEntity<Map<String, BigDecimal>> getAllocation() {
		List<Portfolio> portfolios = portfolioRespository.findAll();
		BigDecimal stockAmt = new BigDecimal(0); 
		BigDecimal bondAmt = new BigDecimal(0); 
		BigDecimal reitAmt = new BigDecimal(0); 
		for (Portfolio portfolio : portfolios) {
			BigDecimal amt = portfolio.getStock().getLatestPrice().multiply(new BigDecimal(portfolio.getQuantity()));
			if (portfolio.getStock().getCategory()==Category.STOCKS) {
				stockAmt = stockAmt.add(amt);
			} else if (portfolio.getStock().getCategory()==Category.BONDS) {
				bondAmt = bondAmt.add(amt);
			} else if (portfolio.getStock().getCategory()==Category.REITS) {
				reitAmt = reitAmt.add(amt);
			}
		}
		
		Map<String, BigDecimal> allocation = new HashMap<String, BigDecimal>();
		allocation.put(Category.STOCKS.name(), stockAmt);
		allocation.put(Category.BONDS.name(), bondAmt);
		allocation.put(Category.REITS.name(), reitAmt);
		return ResponseEntity.ok(allocation);
	}

}
