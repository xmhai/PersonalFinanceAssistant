package com.linh.pfa.job;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.SimpleTrigger;
import org.quartz.Trigger;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

@SpringBootApplication
public class JobServiceApplication {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;
	

	public static void main(String[] args) {
		SpringApplication.run(JobServiceApplication.class, args);
	}

    @Bean
    public SchedulerFactoryBean quartzScheduler() throws Exception {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setOverwriteExistingJobs(true);
        quartzScheduler.setSchedulerName("spring-quartz-scheduler");

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);

    	List<JobConfig> jobConfigs = new ArrayList<JobConfig>();
    	JobConfig jobConfig = new JobConfig();
    	jobConfig.setName("priceupdate");
    	jobConfig.setJobClassName("com.linh.pfa.job.stock.PriceUpdateJob");
    	jobConfig.setCronExpression("0/30 0/1 * 1/1 * ? *");
    	jobConfig.setStartTime(LocalDateTime.now());
    	jobConfigs.add(jobConfig);
        
        List<SimpleTrigger> triggers = new ArrayList<SimpleTrigger>();
        List<JobDetail> jobs = new ArrayList<JobDetail>();
        for (JobConfig config : jobConfigs) {
        	Class clazz = Class.forName(config.getJobClassName());
        	String jobKey = "JobDetail-" + config.getName();
        	JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey, Scheduler.DEFAULT_GROUP).storeDurably().build();
        	jobs.add(jobDetail);

        	SimpleTrigger trigger = newTrigger()
        		    .withIdentity("Trigger-"+jobConfig.getName())
//        		    .withSchedule(cronSchedule(jobConfig.getCronExpression()))
        		    .withSchedule(simpleSchedule()
        		              .withIntervalInSeconds(3)
        		              .repeatForever())	        		    
        		    .forJob(jobDetail)
        		    .build();

        	triggers.add(trigger);
        }
        
        quartzScheduler.setJobDetails(jobs.stream().toArray(JobDetail[]::new));
        quartzScheduler.setTriggers(triggers.stream().toArray(Trigger[]::new));

        return quartzScheduler;
    }
}
