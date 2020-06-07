package com.linh.pfa.account.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.linh.pfa.account.entity.BankTransaction;
import com.linh.pfa.account.entity.BankTransactionRepository;
import com.linh.pfa.account.service.BankTransactionService;

@RestController
@RequestMapping("/bank/transactions")
public class BankTransactionController {
	@Autowired
	private BankTransactionRepository bankTransactionRespository;
	@Autowired
	private BankTransactionService bankTransactionService; 
	
	@GetMapping("")
	public ResponseEntity<List<BankTransaction>> getBankTransactions() {
		return ResponseEntity.ok(bankTransactionRespository.findAll());
	}

    @GetMapping("/{id}")
    public ResponseEntity<BankTransaction> findById(@PathVariable Long id) {
    	BankTransaction bankTransaction = bankTransactionRespository.findById(id).orElse(null);
        if (bankTransaction == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(bankTransaction);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<BankTransaction> upload(@RequestParam("file") MultipartFile file) throws Exception {
		bankTransactionService.save(file);
        return ResponseEntity.ok().build();
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<BankTransaction> update(@PathVariable Long id, @RequestBody BankTransaction bankTransaction) {
        if (bankTransactionRespository.findById(id).orElse(null) == null) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok(bankTransactionRespository.save(bankTransaction));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	BankTransaction bankTransaction = bankTransactionRespository.findById(id).orElse(null);
        if (bankTransaction == null) {
            return ResponseEntity.badRequest().build();
        }

        bankTransactionRespository.delete(bankTransaction);
        return ResponseEntity.ok().build();
    }
}
