package com.linh.pfa.stock.util;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.linh.pfa.stock.entity.Stock;

@Service
public class StockPriceRetrieverChain {
	@Autowired
	private AlphavantageRetriever alphavantageRetriever;
	@Autowired
	private SiHtmlStockPriceRetriever siHtmlStockPriceRetriever;
	
	List<StockPriceRetriever> retrievers = new ArrayList<StockPriceRetriever>();;
	
	@PostConstruct
	public StockPriceRetrieverChain build() {
		retrievers = new ArrayList<StockPriceRetriever>();;
		retrievers.add(alphavantageRetriever);
		retrievers.add(siHtmlStockPriceRetriever);
		return this;
	}
	
	public double getPrice(Stock stock) {
		for (StockPriceRetriever retriever : retrievers) {
			System.out.println("Stock:" + stock.getCode());
			double price = retriever.getPrice(stock);
			if (price > 0) {
				System.out.println("Last Price:" + price);
				return price;
			}
		}
		return 0;
	}
}
