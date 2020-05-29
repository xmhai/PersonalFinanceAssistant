package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;
import com.linh.pfa.common.enums.Category;
import com.linh.pfa.common.enums.Currency;
import com.linh.pfa.common.enums.Exchange;

@Entity
@Where(clause = "is_deleted = 0")
@Getter @Setter @NoArgsConstructor
public class Stock extends BaseEntity {
	@Column(nullable=false, length=10)
	private String code;
	
	@Column(nullable=false, length=60)
	private String name;

	@Column(name="exchange_id", nullable=false)
	private Exchange exchange;
	
	@Column(name="category_id", nullable=false)
	private Category category;
	
	@Column(name="currency_id", nullable=false)
	private Currency currency;
	
	@Column(precision=8, scale=3)
	private BigDecimal latestPrice;
}
