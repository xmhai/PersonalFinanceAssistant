package com.linh.pfa.stock.util;

import org.springframework.stereotype.Service;

import com.linh.pfa.stock.entity.StockEntity;

@Service
public class SiHtmlStockPriceRetriever extends HtmlStockPriceRetriever {
	SiHtmlStockPriceRetriever () {
		String u = "https://www.shareinvestor.com/fundamental/factsheet.html?counter=%s";
		String p = "Last \\(SGD\\): \\<strong\\>(.*?)\\<\\/strong\\>"; 
		setUrl(u, p);
	}
	
	@Override
    public String getStockCode(StockEntity stock) {
        return stock.getCode();
    }
	
	public static void main (String[] args) {
		SiHtmlStockPriceRetriever r = new SiHtmlStockPriceRetriever();
		StockEntity stock = new StockEntity();
		stock.setCode("D05.SI");
		System.out.println(r.getPrice(stock));
	}
}
