package com.linh.common.base;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@MappedSuperclass
@EntityListeners(BaseEntityListener.class)
//hibernate filter is not working on FindById so it will cause security flaw in Restful API
//@FilterDef(name="entityStateFilter")
//@Filter(name="entityStateFilter", condition= "is_deleted = 0")
@Getter
@Setter
@NoArgsConstructor
public class BaseEntity implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@Column(nullable = false, updatable = false)
	private Long createdBy;

	@JsonIgnore
	@Column(nullable = false, updatable = false)
	private LocalDateTime createdDate = LocalDateTime.now();

	@JsonIgnore
	private Long updatedBy;

	@JsonIgnore
	private LocalDateTime updatedDate;

	@JsonIgnore
	@Column(nullable = false)
	private Boolean isDeleted = false;
}
