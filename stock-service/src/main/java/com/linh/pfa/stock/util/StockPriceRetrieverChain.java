package com.linh.pfa.stock.util;

import java.util.ArrayList;
import java.util.List;

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
	
	public StockPriceRetrieverChain build() {
		retrievers = new ArrayList<StockPriceRetriever>();;
		retrievers.add(alphavantageRetriever);
		retrievers.add(siHtmlStockPriceRetriever);
		return this;
	}
	
	public double getPrice(Stock stock) {
		for (StockPriceRetriever retriever : retrievers) {
			double price = retriever.getPrice(stock);
			if (price > 0) {
				System.out.println(price);
				return price;
			}
		}
		return 0;
	}
}
