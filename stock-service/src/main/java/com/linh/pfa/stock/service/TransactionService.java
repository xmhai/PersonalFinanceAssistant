package com.linh.pfa.stock.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.linh.common.base.BusinessException;
import com.linh.pfa.common.Action;
import com.linh.pfa.stock.entity.Portfolio;
import com.linh.pfa.stock.entity.PortfolioRepository;
import com.linh.pfa.stock.entity.Transaction;
import com.linh.pfa.stock.entity.TransactionRepository;

@Service
public class TransactionService {
	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	private PortfolioService portfolioService;
	@Autowired
	private PortfolioRepository portfolioRepository;
	
	@Transactional
	public Transaction create(Transaction transaction) throws BusinessException {
		// update portfolio
		Portfolio portfolio = null;
		if (transaction.getActionId()==Action.BUY.getValue()) {
			portfolio = portfolioService.addPosition(transaction.getStockId(), transaction.getQuantity(), transaction.getPrice());
		} else if (transaction.getActionId()==Action.SELL.getValue()) {
			portfolio = portfolioService.reducePosition(transaction.getStockId(), transaction.getQuantity(), transaction.getPrice());
		}
		
		transaction.setPortfolioId(portfolio.getId());
		return transactionRepository.save(transaction);
	}

	@Transactional
	public void reverse(Long id) throws BusinessException {
    	Transaction transaction = transactionRepository.findById(id).orElse(null);
        if (transaction == null) {
        	throw new BusinessException("Original transaction to reverse does not exist");
        }

        // if this portfolio is closed, cannot reverse
        Portfolio portfolio = portfolioRepository.findById(transaction.getPortfolioId()).orElse(null);
		if (portfolio.getQuantity() == 0) {
        	throw new BusinessException("Portfolio is closed, cannot reverse");
		}
        
		// reverse portfolio
		if (transaction.getActionId()==Action.SELL.getValue()) {
			portfolioService.addPosition(transaction.getStockId(), transaction.getQuantity(), transaction.getPrice());
		} else if (transaction.getActionId()==Action.BUY.getValue()) {
			portfolioService.reducePosition(transaction.getStockId(), transaction.getQuantity(), transaction.getPrice());
		}

		transaction.setIsReversed(true);
        transaction.setIsDeleted(true);
		transactionRepository.save(transaction);
	}
}
