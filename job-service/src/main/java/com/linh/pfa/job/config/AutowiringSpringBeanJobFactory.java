package com.linh.pfa.job.config;

import org.quartz.Job;
import org.quartz.SchedulerContext;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;

public final class AutowiringSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {

	private transient AutowireCapableBeanFactory beanFactory;
	
	private ApplicationContext applicationContext;

    private SchedulerContext schedulerContext;
	
	public void setApplicationContext(final ApplicationContext context) {
		beanFactory = context.getAutowireCapableBeanFactory();
		applicationContext = context;
	}

    public void setSchedulerContext(SchedulerContext schedulerContext) {
        this.schedulerContext = schedulerContext;
        super.setSchedulerContext(schedulerContext);
    }
    
	@Override
	protected Object createJobInstance(final TriggerFiredBundle bundle) throws Exception {
		//final Object job = super.createJobInstance(bundle);
		//beanFactory.autowireBean(job);
		//return job;

        Job job = applicationContext.getBean(bundle.getJobDetail().getJobClass());
        BeanWrapper bw = PropertyAccessorFactory.forBeanPropertyAccess(job);
        MutablePropertyValues pvs = new MutablePropertyValues();
        pvs.addPropertyValues(bundle.getJobDetail().getJobDataMap());
        pvs.addPropertyValues(bundle.getTrigger().getJobDataMap());
        if (this.schedulerContext != null) {
            pvs.addPropertyValues(this.schedulerContext);
        }
        bw.setPropertyValues(pvs, true);
        return job;		
	}
}