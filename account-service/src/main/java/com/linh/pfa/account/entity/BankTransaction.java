package com.linh.pfa.account.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity
@SQLDelete(sql = "UPDATE bank_transaction SET is_deleted = 1 WHERE id = ?")
@Where(clause = "is_deleted = 0")
@DynamicInsert @DynamicUpdate
@Table(indexes = {
		@Index(name = "idx_bank_transaction_date", columnList="transactionDate"),
		@Index(name = "idx_bank_transaction_desc", columnList="description")})
@Getter @Setter @NoArgsConstructor
public class BankTransaction extends BaseEntity {
	@Column(length=4)
	private String bank;
	
	@Column
	private LocalDate transactionDate;
	
	@Column(nullable=false, length=200)
	private String description;
	
	@Column(precision=8, scale=2)
	private BigDecimal debit;
	
	@Column(precision=8, scale=2)
	private BigDecimal credit;
	
	@Column(length=3)
	private String refCode; // POSB reference code
}
