package com.linh.pfa.config.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="exchange")
@Getter @Setter @NoArgsConstructor
public class ExchangeEntity extends BaseEntity {
	@Column(nullable=false, length=10)
	private String code;
}
