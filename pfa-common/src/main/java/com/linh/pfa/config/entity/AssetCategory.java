package com.linh.pfa.config.entity;

import javax.persistence.Column;
import javax.persistence.Entity;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class AssetCategory extends BaseEntity {
	@Column(nullable=false, length=20)
	private String code;
}
