package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.linh.common.base.BaseEntity;

@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE profit SET is_deleted = 0 WHERE id = ?")
@Getter @Setter @NoArgsConstructor
public class Profit extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "stock_id", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private Stock stock;
	
	@Column(precision=8, scale=2)
	private BigDecimal realized = new BigDecimal(0);
	
	@Column(precision=8, scale=2)
	private BigDecimal dividend = new BigDecimal(0);

	public Profit(Stock stock) {
		this.stock = stock;
	}
}
