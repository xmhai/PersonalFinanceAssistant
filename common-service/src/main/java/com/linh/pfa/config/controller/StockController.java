package com.linh.pfa.config.controller;

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

import com.linh.pfa.config.entity.Stock;
import com.linh.pfa.config.entity.StockRepository;

@RestController
@RequestMapping("/stocks")
public class StockController {
	@Autowired
	private StockRepository stockRespository;
	
	@GetMapping("")
	public ResponseEntity<List<Stock>> getStocks() {
		return ResponseEntity.ok(stockRespository.findAll());
	}

    @GetMapping("/{id}")
    public ResponseEntity<Stock> findById(@PathVariable Long id) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (stock == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(stock);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<Stock> create(@RequestBody Stock stock) {
		stock.setCreatedBy(0L);
		return ResponseEntity.ok(stockRespository.saveAndFlush(stock));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody Stock stock) {
    	Stock stockExisting = stockRespository.findById(id).orElse(null);
        if (!stockRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save original values 
        stock.setCreatedBy(stockExisting.getCreatedBy());
        stock.setCreatedDate(stockExisting.getCreatedDate());
        stock.setIsDeleted(false);
        
        stock.setUpdatedBy(0L);
        return ResponseEntity.ok(stockRespository.saveAndFlush(stock));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (stock == null) {
            return ResponseEntity.badRequest().build();
        }

        stock.setUpdatedBy(0L);
        stock.setIsDeleted(true);
        stockRespository.saveAndFlush(stock);
        
        return ResponseEntity.ok().build();
    }
}
