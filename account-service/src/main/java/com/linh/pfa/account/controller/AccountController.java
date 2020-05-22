package com.linh.pfa.account.controller;

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

import com.linh.pfa.account.entity.Account;
import com.linh.pfa.account.entity.AccountRepository;

@RestController
@RequestMapping("/accounts")
public class AccountController {
	@Autowired
	private AccountRepository accountRespository;
	
	@GetMapping("")
	public ResponseEntity<List<Account>> getAccounts() {
		return ResponseEntity.ok(accountRespository.findAll());
	}

    @GetMapping("/{id}")
    public ResponseEntity<Account> findById(@PathVariable Long id) {
    	Account account = accountRespository.findById(id).orElse(null);
        if (account == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(account);
    }

	@PostMapping("")
	@Transactional
	public ResponseEntity<Account> create(@RequestBody Account account) {
		account.setCreatedBy(0L);
		return ResponseEntity.ok(accountRespository.saveAndFlush(account));
	}

    @PutMapping("/{id}")
	@Transactional
    public ResponseEntity<Account> update(@PathVariable Long id, @RequestBody Account account) {
    	Account accountExisting = accountRespository.findById(id).orElse(null);
        if (!accountRespository.findById(id).isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        // save original values 
        account.setCreatedBy(accountExisting.getCreatedBy());
        account.setCreatedDate(accountExisting.getCreatedDate());
        account.setIsDeleted(false);
        
        account.setUpdatedBy(0L);
        return ResponseEntity.ok(accountRespository.saveAndFlush(account));
    }

    @DeleteMapping("/{id}")
	@Transactional
    public ResponseEntity delete(@PathVariable Long id) {
    	Account account = accountRespository.findById(id).orElse(null);
        if (account == null) {
            return ResponseEntity.badRequest().build();
        }

        account.setUpdatedBy(0L);
        account.setIsDeleted(true);
        accountRespository.saveAndFlush(account);
        
        return ResponseEntity.ok().build();
    }
}
