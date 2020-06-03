package com.linh.pfa.job.config;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import com.linh.common.base.BaseEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Where(clause = "is_deleted = 0")
@SQLDelete(sql = "UPDATE job_log SET is_deleted = 0 WHERE id = ?")
@Getter @Setter @NoArgsConstructor
public class JobLog extends BaseEntity {
	@Column(nullable=false)
	private LocalDateTime startTime = LocalDateTime.now();
	
	@Column
	private LocalDateTime endTime;
	
	@Column(nullable=false, length=60)
	private String jobName;
	
	@Column(length=200)
	private String message;
	
	@Column(nullable = false)
	private Boolean isCompleted = false;
}