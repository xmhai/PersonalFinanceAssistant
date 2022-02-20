package com.linh.pfa.stock.controller;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.linh.pfa.stock.entity.Stock;
import com.linh.pfa.stock.entity.StockRepository;
import com.linh.pfa.stock.service.StockService;


@RestController
@RequestMapping("/stocks")
public class StockController {
	@Autowired
	private StockService stockService;
	@Autowired
	private StockRepository stockRespository;
	
	@GetMapping("")
	@Cacheable("stocks")
	public List<Stock> getStocks() {
		return stockRespository.findAll(Sort.by(Sort.Direction.ASC, "name"));
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
    @CacheEvict(value = "stocks", allEntries = true)
	public ResponseEntity<Stock> create(@RequestBody Stock stock) {
		return ResponseEntity.ok(stockService.create(stock));
	}

    @PutMapping("/{id}")
	@Transactional
    @CacheEvict(value = "stocks", allEntries = true)
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody Stock stock) {
    	Stock stockOld = stockRespository.findById(id).orElse(null);
        if (stockOld == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stockService.update(stockOld, stock));
    }

    @DeleteMapping("/{id}")
    @CacheEvict(value = "stocks", allEntries = true)
    public ResponseEntity delete(@PathVariable Long id) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (stock == null) {
            return ResponseEntity.badRequest().build();
        }

        stockRespository.delete(stock);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refresh/price")
    @CacheEvict(value = "stocks", allEntries = true)
    public ResponseEntity<Double> refreshPrice() {
    	stockService.refreshPrice();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refresh/price/{id}")
    @CacheEvict(value = "stocks", allEntries = true)
    public ResponseEntity<Double> refreshPrice(@PathVariable Long id) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (!stockRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Get current price and update to stock table
        stockService.refreshPrice(stock);

        return ResponseEntity.ok(stock.getLatestPrice().doubleValue());
    }

    @GetMapping("/latestprice/{id}")
    public ResponseEntity<BigDecimal> getLatestPrice(@PathVariable Long id) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (!stockRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        return ResponseEntity.ok(stockService.getLatestPrice(stock));
    }
}
