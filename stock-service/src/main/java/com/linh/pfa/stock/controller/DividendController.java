package com.linh.pfa.stock.controller;

import java.util.List;

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

import com.linh.pfa.stock.entity.Dividend;
import com.linh.pfa.stock.entity.DividendRepository;

@RestController
@RequestMapping("/dividends")
public class DividendController {
	@Autowired
	private DividendRepository dividendRespository;
	
	@GetMapping("")
	public ResponseEntity<List<Dividend>> getDividends() {
		return ResponseEntity.ok(dividendRespository.findAll());
	}

    @GetMapping("/{id}")
    public ResponseEntity<Dividend> findById(@PathVariable Long id) {
    	Dividend dividend = dividendRespository.findById(id).orElse(null);
        if (dividend == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(dividend);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<Dividend> create(@RequestBody Dividend dividend) {
		dividend.setCreatedBy(0L);
		return ResponseEntity.ok(dividendRespository.saveAndFlush(dividend));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Dividend> update(@PathVariable Long id, @RequestBody Dividend dividend) {
    	Dividend dividendExisting = dividendRespository.findById(id).orElse(null);
        if (!dividendRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save original values 
        dividend.setCreatedBy(dividendExisting.getCreatedBy());
        dividend.setCreatedDate(dividendExisting.getCreatedDate());
        dividend.setIsDeleted(false);
        
        dividend.setUpdatedBy(0L);
        return ResponseEntity.ok(dividendRespository.saveAndFlush(dividend));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Dividend dividend = dividendRespository.findById(id).orElse(null);
        if (dividend == null) {
            return ResponseEntity.badRequest().build();
        }

        dividend.setUpdatedBy(0L);
        dividend.setIsDeleted(true);
        dividendRespository.saveAndFlush(dividend);
        
        return ResponseEntity.ok().build();
    }
}
