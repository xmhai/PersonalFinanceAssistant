package com.linh.pfa.account.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@SQLDelete(sql = "UPDATE asset_history SET is_deleted = 1 WHERE id = ?")
@Where(clause = "is_deleted = 0")
@DynamicInsert @DynamicUpdate
@Getter @Setter @NoArgsConstructor
public class AssetHistory extends BaseEntity {
	@Column
	private LocalDate recordDate;
	
	@Column(precision=9, scale=2)
	private BigDecimal cash;

	@Column(precision=9, scale=2)
	private BigDecimal stocks;

	@Column(precision=9, scale=2)
	private BigDecimal reits;

	@Column(precision=9, scale=2)
	private BigDecimal bonds;

	@Column(precision=9, scale=2)
	private BigDecimal cpfOrdinary;

	@Column(precision=9, scale=2)
	private BigDecimal cpfMedisave;

	@Column(precision=9, scale=2)
	private BigDecimal cpfSpecial;

	@Column(precision=9, scale=2)
	private BigDecimal total;
}