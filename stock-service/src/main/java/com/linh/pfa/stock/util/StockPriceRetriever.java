package com.linh.pfa.stock.util;

import com.linh.pfa.stock.entity.Stock;

public interface StockPriceRetriever {
	double getPrice(Stock stock);
}
