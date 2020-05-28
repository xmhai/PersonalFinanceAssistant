package com.linh.pfa.config.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Currency extends BaseEntity {
	@Column(nullable=false, length=3)
	private String code;
	
	private BigDecimal exchangeRate;
}
