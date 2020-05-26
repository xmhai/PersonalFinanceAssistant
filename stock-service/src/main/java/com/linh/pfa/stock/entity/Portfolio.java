package com.linh.pfa.stock.entity;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.Where;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linh.common.base.BaseEntity;

@Entity
@Where(clause = "is_deleted = 0")
@Getter @Setter @NoArgsConstructor
public class Portfolio extends BaseEntity {
	@Column(nullable=false)
	private Long stockId;
	
	@Column(nullable=false)
	private Integer quantity; // if quantity, means this portfolio is closed
	
	@Column(nullable=false, precision=8, scale=3)
	private BigDecimal cost;
	
	@JsonIgnore
	@Column(precision=8, scale=3)
	private BigDecimal realizedPrice;
	
	public Portfolio(Long stockId, Integer quantity, BigDecimal cost) {
		this.stockId = stockId; 
		this.quantity = quantity; 
		this.cost = cost; 
	}
	
	public void add(int qty, BigDecimal price) {
		int newPosition = quantity + qty;
		BigDecimal newTotalCost = cost.multiply(new BigDecimal(quantity)).add(price.multiply(new BigDecimal(qty)));   
		quantity = newPosition;
		cost =  newTotalCost.divide(new BigDecimal(newPosition)); 
	}

	public void reduce(int qty, BigDecimal price) {
		int newPosition = quantity - qty;
		BigDecimal newTotalCost = cost.multiply(new BigDecimal(quantity)).subtract(price.multiply(new BigDecimal(qty)));   
		quantity = newPosition;
		cost =  newTotalCost.divide(new BigDecimal(newPosition)); 
	}
}
