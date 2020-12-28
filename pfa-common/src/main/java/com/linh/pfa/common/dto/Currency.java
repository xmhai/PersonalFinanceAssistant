package com.linh.pfa.common.dto;

import java.math.BigDecimal;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class Currency {
	private Long id;
	private String code;
	private BigDecimal exchangeRate;
}
