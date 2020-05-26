package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity
@Where(clause = "is_deleted = 0")
@Getter @Setter @NoArgsConstructor
public class Profit extends BaseEntity {
	@Column(nullable=false)
	private Long stockId;
	
	@Column(precision=8, scale=2)
	private BigDecimal realized = new BigDecimal(0);
	
	@Column(precision=8, scale=2)
	private BigDecimal dividend = new BigDecimal(0);

	public Profit(Long stockId) {
		this.stockId = stockId;
	}
}
