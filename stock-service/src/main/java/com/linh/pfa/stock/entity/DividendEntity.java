package com.linh.pfa.stock.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity(name="dividend")
@SQLDelete(sql = "UPDATE dividend SET is_deleted = 1 WHERE id = ?")
@Where(clause = "is_deleted = 0")
@DynamicInsert @DynamicUpdate
@Getter @Setter @NoArgsConstructor
public class DividendEntity extends BaseEntity {
	@Column(nullable=false)
	private Long stockId;
	
	@Column(nullable=false)
	private LocalDate payDate;
	
	// in SGD as the value is from Bank Statement
	@Column(precision=8, scale=2)
	private BigDecimal amount;
}
