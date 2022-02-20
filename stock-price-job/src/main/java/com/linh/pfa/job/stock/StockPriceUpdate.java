package com.linh.pfa.job.stock;

import java.math.BigDecimal;

import lombok.Data;

@Data
public class StockPriceUpdate {
	private Long id;
	private BigDecimal latestPrice;
}
