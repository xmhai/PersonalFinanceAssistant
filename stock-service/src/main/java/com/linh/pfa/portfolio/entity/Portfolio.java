package com.linh.pfa.portfolio.entity;

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
public class Portfolio extends BaseEntity {
	private String instituteName;
	private String portfolioNo;
	private String portfolioHolder;
	private Long categoryId;
	private Long currencyId;
	private BigDecimal amount;
	private LocalDate maturityDate;
}
