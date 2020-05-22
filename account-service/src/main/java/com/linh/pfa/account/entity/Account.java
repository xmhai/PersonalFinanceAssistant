package com.linh.pfa.account.entity;

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
public class Account extends BaseEntity {
	private String instituteName;
	private String accountNo;
	private String accountHolder;
	private Long categoryId;
	private Long currencyId;
	private BigDecimal amount;
	private LocalDate maturityDate;
}
