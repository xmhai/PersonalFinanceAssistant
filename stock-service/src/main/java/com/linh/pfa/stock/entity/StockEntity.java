package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;
import com.linh.pfa.common.enums.Category;
import com.linh.pfa.common.enums.Currency;
import com.linh.pfa.common.enums.Exchange;

@Entity(name="stock")
@SQLDelete(sql = "UPDATE stock SET is_deleted = 1 WHERE id = ?")
@Where(clause = "is_deleted = 0")
@DynamicInsert @DynamicUpdate
@Getter @Setter @NoArgsConstructor
@Cacheable
public class StockEntity extends BaseEntity {
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
