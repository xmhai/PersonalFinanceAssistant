package com.linh.pfa.config.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class Exchange extends BaseEntity {
	@Column(nullable=false, length=10)
	private String code;
}
