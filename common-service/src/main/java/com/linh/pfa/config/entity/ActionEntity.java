package com.linh.pfa.config.entity;

import javax.persistence.Entity;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity(name="action")
@Getter @Setter @NoArgsConstructor
public class ActionEntity extends BaseEntity {
	private String code;
}
