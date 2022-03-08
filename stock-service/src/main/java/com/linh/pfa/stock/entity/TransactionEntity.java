package com.linh.pfa.stock.entity;

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
import com.linh.pfa.common.enums.Action;

@Entity(name="transaction")
@SQLDelete(sql = "UPDATE transaction SET is_deleted = 1 WHERE id = ?")
@Where(clause = "is_deleted = 0")
@Table(indexes = {
		@Index(name = "idx_transaction_stock_id", columnList="stockId"),
		@Index(name = "idx_transaction_portfolio_id", columnList="portfolioId")})
@DynamicInsert @DynamicUpdate
@Getter @Setter @NoArgsConstructor
public class TransactionEntity extends BaseEntity {
	@Column(nullable=false)
	private Long stockId;
	
	@Column(nullable=false)
	private LocalDate transactionDate;
	
	@Column(name="action_id", nullable=false)
	private Action action;
	
	@Column(nullable=false, precision=8, scale=3)
	private BigDecimal price;
	
	@Column(nullable=false)
	private Integer quantity;
	
	@Column(nullable=false)
	private Long portfolioId;
	
	@Column
	private Boolean isReversed = false;
}
