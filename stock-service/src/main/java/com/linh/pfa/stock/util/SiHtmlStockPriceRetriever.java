package com.linh.pfa.stock.util;

import org.springframework.stereotype.Service;

import com.linh.pfa.stock.entity.Stock;

@Service
public class SiHtmlStockPriceRetriever extends HtmlStockPriceRetriever {
	SiHtmlStockPriceRetriever () {
		String u = "https://www.shareinvestor.com/fundamental/factsheet.html?counter=%s";
		String p = "Last \\(SGD\\): \\<strong\\>(.*?)\\<\\/strong\\>"; 
		setUrl(u, p);
	}
	
	@Override
    public String getStockCode(Stock stock) {
        return stock.getCode();
    }
	
	public static void main (String[] args) {
		SiHtmlStockPriceRetriever r = new SiHtmlStockPriceRetriever();
		Stock stock = new Stock();
		stock.setCode("D05.SI");
		System.out.println(r.getPrice(stock));
	}
}
