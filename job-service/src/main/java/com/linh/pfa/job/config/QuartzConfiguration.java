package com.linh.pfa.job.config;

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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.linh.pfa.job.config.AutowiringSpringBeanJobFactory;
import com.linh.pfa.job.config.JobConfig;

import static org.quartz.TriggerBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.JobBuilder.*;
import static org.quartz.SimpleScheduleBuilder.*;
import static org.quartz.CronScheduleBuilder.*;
import static org.quartz.CalendarIntervalScheduleBuilder.*;
import static org.quartz.TriggerBuilder.*;
import static org.quartz.DateBuilder.*;

@Configuration
public class QuartzConfiguration {

	@Autowired
	private ApplicationContext applicationContext;
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private JobConfigRepository jobConfigRepository;
	
    @Bean
    public SchedulerFactoryBean quartzScheduler() throws Exception {
        SchedulerFactoryBean quartzScheduler = new SchedulerFactoryBean();

        quartzScheduler.setDataSource(dataSource);
        quartzScheduler.setOverwriteExistingJobs(true);
        quartzScheduler.setSchedulerName("spring-quartz-scheduler");

        AutowiringSpringBeanJobFactory jobFactory = new AutowiringSpringBeanJobFactory();
        jobFactory.setApplicationContext(applicationContext);
        quartzScheduler.setJobFactory(jobFactory);

    	List<JobConfig> jobConfigs = jobConfigRepository.findAll();
        List<SimpleTrigger> triggers = new ArrayList<SimpleTrigger>();
        List<JobDetail> jobs = new ArrayList<JobDetail>();
        for (JobConfig config : jobConfigs) {
        	Class clazz = Class.forName(config.getJobClassName());
        	String jobKey = "JobDetail-" + config.getName();
        	JobDetail jobDetail = JobBuilder.newJob(clazz).withIdentity(jobKey).storeDurably().build();
        	jobs.add(jobDetail);

        	SimpleTrigger trigger = newTrigger()
        		    .withIdentity("Trigger-"+config.getName())
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
