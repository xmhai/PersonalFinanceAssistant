package com.linh.pfa.job.config;

import java.time.LocalDateTime;

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
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE job_config SET is_deleted = 1 WHERE id = ?")
@DynamicInsert @DynamicUpdate
@Getter @Setter @NoArgsConstructor
public class JobConfig extends BaseEntity {
	@Column(nullable=false, length=60)
	private String name;
	
	@Column(length=200)
	private String description;
	
	@Column(nullable=false, length=200)
	private String jobClassName;
	
	@Column(nullable=false, length=60)
	private String cronExpression;
	
	@Column
	private LocalDateTime startTime;
	
	@Column
	private LocalDateTime endTime;
}
