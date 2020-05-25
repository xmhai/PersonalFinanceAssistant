package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

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
	private Long stockId;
	private BigDecimal realized;
	private BigDecimal dividend;

	public Profit(Long stockId) {
		this.stockId = stockId;
		realized = new BigDecimal(0);
		dividend = new BigDecimal(0);
	}
}
