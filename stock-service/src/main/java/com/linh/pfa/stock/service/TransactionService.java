package com.linh.pfa.stock.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.common.base.BusinessException;
import com.linh.pfa.common.Action;
import com.linh.pfa.stock.entity.Transaction;
import com.linh.pfa.stock.entity.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRespository;
	@Autowired
	private PortfolioService portfolioService;
	
	@Transactional
	public Transaction create(Transaction transaction) throws BusinessException {
		// update portfolio
		if (transaction.getActionId()==Action.BUY.getValue()) {
			portfolioService.addPosition(transaction.getStockId(), transaction.getQuantity(), transaction.getPrice());
		} else if (transaction.getActionId()==Action.SELL.getValue()) {
			portfolioService.reducePosition(transaction.getStockId(), transaction.getQuantity(), transaction.getPrice());
		}
		
		return transactionRespository.save(transaction);
	}
}
