package com.linh.pfa.portfolio.controller;

import java.util.List;
import java.util.Optional;

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

import com.linh.pfa.portfolio.entity.Portfolio;
import com.linh.pfa.portfolio.entity.PortfolioRepository;

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
		portfolio.setCreatedBy(0L);
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
        
        portfolio.setUpdatedBy(0L);
        return ResponseEntity.ok(portfolioRespository.saveAndFlush(portfolio));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Portfolio portfolio = portfolioRespository.findById(id).orElse(null);
        if (portfolio == null) {
            return ResponseEntity.badRequest().build();
        }

        portfolio.setUpdatedBy(0L);
        portfolio.setIsDeleted(true);
        portfolioRespository.saveAndFlush(portfolio);
        
        return ResponseEntity.ok().build();
    }
}
