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

import com.linh.pfa.stock.entity.Profit;
import com.linh.pfa.stock.entity.ProfitRepository;

@RestController
@RequestMapping("/profits")
public class ProfitController {
	@Autowired
	private ProfitRepository profitRespository;
	
	@GetMapping("")
	public ResponseEntity<List<Profit>> getProfits() {
		return ResponseEntity.ok(profitRespository.findAll());
	}

    @GetMapping("/{id}")
    public ResponseEntity<Profit> findById(@PathVariable Long id) {
    	Profit profit = profitRespository.findById(id).orElse(null);
        if (profit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profit);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<Profit> create(@RequestBody Profit profit) {
		profit.setCreatedBy(0L);
		return ResponseEntity.ok(profitRespository.saveAndFlush(profit));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Profit> update(@PathVariable Long id, @RequestBody Profit profit) {
    	Profit profitExisting = profitRespository.findById(id).orElse(null);
        if (!profitRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save original values 
        profit.setCreatedBy(profitExisting.getCreatedBy());
        profit.setCreatedDate(profitExisting.getCreatedDate());
        profit.setIsDeleted(false);
        
        profit.setUpdatedBy(0L);
        return ResponseEntity.ok(profitRespository.saveAndFlush(profit));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Profit profit = profitRespository.findById(id).orElse(null);
        if (profit == null) {
            return ResponseEntity.badRequest().build();
        }

        profit.setUpdatedBy(0L);
        profit.setIsDeleted(true);
        profitRespository.saveAndFlush(profit);
        
        return ResponseEntity.ok().build();
    }
}
