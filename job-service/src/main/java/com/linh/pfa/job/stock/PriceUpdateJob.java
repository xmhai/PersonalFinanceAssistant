package com.linh.pfa.job.stock;

import java.time.LocalDateTime;

import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.linh.pfa.job.config.JobLog;
import com.linh.pfa.job.config.JobLogRepository;

@Component
@DisallowConcurrentExecution
public class PriceUpdateJob implements Job {
	@Autowired
	private PriceUpdateService priceUpdateService;
	
	@Autowired
	private JobLogRepository jobLogRepository; 

	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		System.out.println("PriceUpdateJob is running...");
		
		JobLog jobLog = new JobLog();
		jobLog.setJobName(context.getJobDetail().getKey().getName());
		jobLog = jobLogRepository.save(jobLog);
		
		priceUpdateService.execute(); // 0 0 18 ? * MON-FRI === Mon to Fri 6pm

		jobLog.setEndTime(LocalDateTime.now());
		jobLog.setIsCompleted(true);
		jobLog.setMessage("success");
		jobLog = jobLogRepository.save(jobLog);
		
		System.out.println("PriceUpdateJob is completed");
	} 

}
