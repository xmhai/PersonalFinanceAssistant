package com.linh.pfa.account.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {
	@GetMapping("/account")
	public String getAccounts() {
		return "Hi There";
	}
}
