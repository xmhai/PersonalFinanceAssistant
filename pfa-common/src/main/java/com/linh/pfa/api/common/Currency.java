package com.linh.pfa.api.common;

import java.math.BigDecimal;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@NoArgsConstructor
@ToString
public class Currency {
	private Long id;
	private String code;
	private BigDecimal exchangeRate;
}
