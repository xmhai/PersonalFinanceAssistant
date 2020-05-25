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

import com.linh.pfa.stock.entity.Transaction;
import com.linh.pfa.stock.entity.TransactionRepository;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
	@Autowired
	private TransactionRepository transactionRespository;
	
	@GetMapping("")
	public ResponseEntity<List<Transaction>> getTransactions() {
		return ResponseEntity.ok(transactionRespository.findAll());
	}

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> findById(@PathVariable Long id) {
    	Transaction transaction = transactionRespository.findById(id).orElse(null);
        if (transaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(transaction);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<Transaction> create(@RequestBody Transaction transaction) {
		transaction.setCreatedBy(0L);
		return ResponseEntity.ok(transactionRespository.saveAndFlush(transaction));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Transaction> update(@PathVariable Long id, @RequestBody Transaction transaction) {
    	Transaction transactionExisting = transactionRespository.findById(id).orElse(null);
        if (!transactionRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save original values 
        transaction.setCreatedBy(transactionExisting.getCreatedBy());
        transaction.setCreatedDate(transactionExisting.getCreatedDate());
        transaction.setIsDeleted(false);
        
        transaction.setUpdatedBy(0L);
        return ResponseEntity.ok(transactionRespository.saveAndFlush(transaction));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Transaction transaction = transactionRespository.findById(id).orElse(null);
        if (transaction == null) {
            return ResponseEntity.badRequest().build();
        }

        transaction.setUpdatedBy(0L);
        transaction.setIsDeleted(true);
        transactionRespository.saveAndFlush(transaction);
        
        return ResponseEntity.ok().build();
    }
}