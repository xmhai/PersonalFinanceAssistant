package com.linh.pfa.config.entity;

import javax.persistence.Entity;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class TransactionType extends BaseEntity {
	private String code;
}
