package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;
import com.linh.pfa.config.entity.AssetCategory;
import com.linh.pfa.config.entity.Currency;
import com.linh.pfa.config.entity.Exchange;

@Entity
@Where(clause = "is_deleted = 0")
@Getter @Setter @NoArgsConstructor
public class Stock extends BaseEntity {
	@Column(nullable=false, length=10)
	private String code;
	
	@Column(nullable=false, length=60)
	private String name;

	@ManyToOne
	@JoinColumn(name = "exchange_id", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private Exchange exchange;
	
	@ManyToOne
	@JoinColumn(name = "category_id", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private AssetCategory category;
	
	@ManyToOne
	@JoinColumn(name = "currency_id", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private Currency currency;
	
	@Column(precision=8, scale=3)
	private BigDecimal latestPrice;
}
