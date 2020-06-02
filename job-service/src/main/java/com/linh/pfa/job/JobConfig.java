package com.linh.pfa.job;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor
public class JobConfig {
	private String name;
	private String description;
	private String jobClassName;
	private String cronExpression;
	private LocalDateTime startTime;
	private LocalDateTime endTime;
}
