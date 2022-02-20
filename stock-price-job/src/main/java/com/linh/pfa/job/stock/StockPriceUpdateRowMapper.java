package com.linh.pfa.job.stock;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class StockPriceUpdateRowMapper implements RowMapper<StockPriceUpdate> {

	@Override
	public StockPriceUpdate mapRow(ResultSet rs, int rowNum) throws SQLException {
		StockPriceUpdate stockPriceUpdate = new StockPriceUpdate();
		stockPriceUpdate.setId(rs.getLong("stock_id"));
		return stockPriceUpdate;
	}

}
