package com.linh.pfa.stock.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Entity;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity
@Where(clause = "is_deleted = 0")
@Getter @Setter @NoArgsConstructor
public class Dividend extends BaseEntity {
	private Long stockId;
	private LocalDate payDate;
	private BigDecimal amount;
}
