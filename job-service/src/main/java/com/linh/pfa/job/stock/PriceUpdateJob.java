package com.linh.pfa.job.stock;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PriceUpdateJob implements Job {
	@Autowired
	private PriceUpdateService priceUpdateService;

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		priceUpdateService.execute();
	} 

}
