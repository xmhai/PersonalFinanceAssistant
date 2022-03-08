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

import com.linh.common.base.BusinessException;
import com.linh.pfa.stock.entity.TransactionEntity;
import com.linh.pfa.stock.entity.TransactionRepository;
import com.linh.pfa.stock.service.TransactionService;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	private TransactionService transactionService;
	@Autowired
	private TransactionRepository transactionRespository;
	
	@GetMapping("")
	public ResponseEntity<List<TransactionEntity>> getTransactions() {
		return ResponseEntity.ok(transactionRespository.findAll(Sort.by(Sort.Direction.DESC, "transactionDate")));
	}

    @GetMapping("/{id}")
    public ResponseEntity<TransactionEntity> findById(@PathVariable Long id) {
    	TransactionEntity transaction = transactionRespository.findById(id).orElse(null);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transaction);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<TransactionEntity> create(@RequestBody TransactionEntity transaction) throws BusinessException {
		return ResponseEntity.ok(transactionService.create(transaction));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<TransactionEntity> update(@PathVariable Long id, @RequestBody TransactionEntity transaction) {
        if (transactionRespository.findById(id).orElse(null) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(transactionRespository.save(transaction));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) throws BusinessException {
        transactionService.reverse(id);
        return ResponseEntity.ok().build();
    }
}
