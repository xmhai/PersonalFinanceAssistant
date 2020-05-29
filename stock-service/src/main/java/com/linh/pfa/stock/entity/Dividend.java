package com.linh.pfa.stock.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE dividend SET is_deleted = 0 WHERE id = ?")
@Getter @Setter @NoArgsConstructor
public class Dividend extends BaseEntity {
	@Column(nullable=false)
	private Long stockId;
	
	@Column(nullable=false)
	private LocalDate payDate;
	
	@Column(precision=8, scale=2)
	private BigDecimal amount;
}
