package com.linh.pfa.job.config;

import static org.quartz.CronScheduleBuilder.cronSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SchedulerService
{
	@Autowired
    private Scheduler scheduler;
	
	@Autowired
	private JobConfigRepository jobConfigRepository;

	@PostConstruct
    public void createSchedule() throws Exception {
		scheduler.clear();
		
    	List<JobConfig> jobConfigs = jobConfigRepository.findAll();
        for (JobConfig config : jobConfigs) {
        	Class clazz = Class.forName(config.getJobClassName());
        	String jobKey = "JobDetail-" + config.getName();
        	JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).storeDurably().build();

        	CronTrigger trigger = newTrigger()
        		    .withIdentity("Trigger-"+config.getName())
        		    .withSchedule(cronSchedule(config.getCronExpression()))
//        		    .withSchedule(SimpleScheduleBuilder.simpleSchedule()
//        		              .withIntervalInSeconds(5)
//        		              .repeatForever())	        		    
        		    .forJob(jobDetail)
        		    .build();

            scheduler.scheduleJob(jobDetail, trigger);
        }
        scheduler.start();
    }

    @PreDestroy
    public void preDestroy() throws SchedulerException {
    	try {
    		System.out.println("scheduler is shutting down...");
	        scheduler.shutdown(true);
    	} catch (Exception e) {
    		e.printStackTrace();
	        scheduler.shutdown(false);
    	}
    }
}