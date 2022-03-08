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

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.linh.common.base.BaseEntity;

@Entity(name="portfolio")
@SQLDelete(sql = "UPDATE portfolio SET is_deleted = 1 WHERE id = ?")
@Where(clause = "is_deleted = 0")
@DynamicInsert @DynamicUpdate
@Getter @Setter @NoArgsConstructor
public class PortfolioEntity extends BaseEntity {
	@ManyToOne
	@JoinColumn(name = "stock_id", foreignKey = @javax.persistence.ForeignKey(name = "none"))
	private StockEntity stock;
	
	@Column(nullable=false)
	private Integer quantity; // if quantity, means this portfolio is closed
	
	@Column(nullable=false, precision=8, scale=3)
	private BigDecimal cost;
	
	@JsonIgnore
	@Column(precision=8, scale=3)
	private BigDecimal realizedPrice;
	
	public PortfolioEntity(StockEntity stock, Integer quantity, BigDecimal cost) {
		this.stock = stock; 
		this.quantity = quantity; 
		this.cost = cost; 
	}
	
	public void add(int qty, BigDecimal price) {
		int newPosition = quantity + qty;
		BigDecimal newTotalCost = cost.multiply(new BigDecimal(quantity)).add(price.multiply(new BigDecimal(qty)));   
		quantity = newPosition;
		cost =  newTotalCost.divide(new BigDecimal(newPosition), 8, BigDecimal.ROUND_UP); 
	}

	public void reduce(int qty, BigDecimal price) {
		int newPosition = quantity - qty;
		BigDecimal newTotalCost = cost.multiply(new BigDecimal(quantity)).subtract(price.multiply(new BigDecimal(qty)));   
		quantity = newPosition;
		cost =  newTotalCost.divide(new BigDecimal(newPosition), 8, BigDecimal.ROUND_UP); 
	}

	public void close(BigDecimal price) {
		realizedPrice = price;
	}
}
