package com.linh.pfa.stock.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.OneToOne;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity(name = "stock_transaction")
@Where(clause = "is_deleted = 0")
@Getter @Setter @NoArgsConstructor
public class Transaction extends BaseEntity {
	@OneToOne
	private Stock stock;
	private LocalDate transactionDate;
	private Long tranctionTypeId; 
	private BigDecimal price;
	private Integer quantity;
}
