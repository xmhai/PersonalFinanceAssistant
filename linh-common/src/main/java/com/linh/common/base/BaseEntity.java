package com.linh.common.base;

import java.time.LocalDateTime;

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
@Getter @Setter @NoArgsConstructor
public class BaseEntity {
	  @Id
	  @GeneratedValue(strategy=GenerationType.AUTO)
	  private Long id;

	  @JsonIgnore
	  private Long createdBy;
	  
	  @JsonIgnore
	  private LocalDateTime createdDate;
	  
	  @JsonIgnore
	  private Long updatedBy;
	  
	  @JsonIgnore
	  private LocalDateTime updatedDate;
	  
	  @JsonIgnore
	  private Boolean isDeleted;
}
