package com.linh.pfa.stock.entity;

import java.math.BigDecimal;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity
@Where(clause = "is_deleted = 0")
@Getter @Setter @NoArgsConstructor
public class Portfolio extends BaseEntity {
	@OneToOne
	private Stock stock;
	private Long quantity;
	private BigDecimal cost;
}
