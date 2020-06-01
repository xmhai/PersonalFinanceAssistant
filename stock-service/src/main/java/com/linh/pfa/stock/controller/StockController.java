package com.linh.pfa.stock.controller;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
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
	public ResponseEntity<List<Stock>> getStocks() {
		return ResponseEntity.ok(stockRespository.findAll(Sort.by(Sort.Direction.ASC, "name")));
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
		return ResponseEntity.ok(stockService.create(stock));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Stock> update(@PathVariable Long id, @RequestBody Stock s) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (!stockRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(stockService.update(stock, s));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Long id) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (stock == null) {
            return ResponseEntity.badRequest().build();
        }

        stockRespository.delete(stock);
        
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refresh/price")
    public ResponseEntity<Double> refreshPrice() {
    	stockService.refreshPrice();
        return ResponseEntity.ok().build();
    }

    @PutMapping("/refresh/price/{id}")
    public ResponseEntity<Double> refreshPrice(@PathVariable Long id) {
    	Stock stock = stockRespository.findById(id).orElse(null);
        if (!stockRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }
        
        // Get current price and update to stock table
        stockService.refreshPrice(stock);

        return ResponseEntity.ok(stock.getLatestPrice().doubleValue());
    }
}
