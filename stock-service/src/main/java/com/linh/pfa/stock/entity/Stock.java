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
public class Stock extends BaseEntity {
	private String code;
	private String name;
	private Long exchangeId;
	private Long categoryId;
	private Long currencyId;
	private BigDecimal latestPrice;
}
