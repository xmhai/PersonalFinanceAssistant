package com.linh.pfa.stock.controller;

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

import com.linh.pfa.stock.entity.ProfitEntity;
import com.linh.pfa.stock.entity.ProfitRepository;
import com.linh.pfa.stock.service.ProfitService;

@RestController
@RequestMapping("/profits")
public class ProfitController {
	@Autowired
	private ProfitService profitService;
	@Autowired
	private ProfitRepository profitRespository;
	
	@GetMapping("")
	public List<Map<String, Object>> getProfits() {
		return profitService.getProfits();
	}

    @GetMapping("/{id}")
    public ResponseEntity<ProfitEntity> findById(@PathVariable Long id) {
    	ProfitEntity profit = profitRespository.findById(id).orElse(null);
        if (profit == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(profit);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<ProfitEntity> create(@RequestBody ProfitEntity profit) {
		return ResponseEntity.ok(profitRespository.save(profit));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<ProfitEntity> update(@PathVariable Long id, @RequestBody ProfitEntity profit) {
        if (profitRespository.findById(id).orElse(null) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(profitRespository.save(profit));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	ProfitEntity profit = profitRespository.findById(id).orElse(null);
        if (profit == null) {
            return ResponseEntity.badRequest().build();
        }

        profitRespository.delete(profit);
        return ResponseEntity.ok().build();
    }
}
