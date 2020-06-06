package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity
@SQLDelete(sql = "UPDATE profit SET is_deleted = 1 WHERE id = ?")
@Where(clause = "is_deleted = 0")
@DynamicInsert @DynamicUpdate
@Getter @Setter @NoArgsConstructor
public class Profit extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "stock_id", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private Stock stock;
	
	// in SGD as it should reflect the earning at the time when profit is realized
	@Column(precision=8, scale=2)
	private BigDecimal realized = new BigDecimal(0);	// in SGD
	
	// in SGD as the value is the summary of dividend table
	@Column(precision=8, scale=2)
	private BigDecimal dividend = new BigDecimal(0);

	public Profit(Stock stock) {
		this.stock = stock;
	}
}
