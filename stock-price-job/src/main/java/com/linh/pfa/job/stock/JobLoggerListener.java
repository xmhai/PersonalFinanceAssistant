package com.linh.pfa.job.stock;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JobLoggerListener implements JobExecutionListener {
	@Override
	public void beforeJob(JobExecution jobExecution) {
		log.info(String.format("%s is beginning execution", jobExecution.getJobInstance().getJobName()));
	}

	@Override
	public void afterJob(JobExecution jobExecution) {
		log.info(String.format("%s has completed with the status %s",
				jobExecution.getJobInstance().getJobName(),
				jobExecution.getStatus()));
	}
}