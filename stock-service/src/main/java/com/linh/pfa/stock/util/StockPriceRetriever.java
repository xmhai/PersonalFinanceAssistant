package com.linh.pfa.stock.util;

import com.linh.pfa.stock.entity.StockEntity;

public interface StockPriceRetriever {
	double getPrice(StockEntity stock);
}
