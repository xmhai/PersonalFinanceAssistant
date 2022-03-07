package com.linh.pfa.config.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="currency")
@Getter @Setter @NoArgsConstructor
public class CurrencyEntity extends BaseEntity {
	@Column(nullable=false, length=3)
	private String code;
	
	private BigDecimal exchangeRate;
}
