package com.fpcms.scheduled.job;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.util.Assert;

public abstract class BaseCronJob implements InitializingBean{

	private static Logger logger = LoggerFactory.getLogger(BaseCronJob.class);
	private String cron;
	private Date lastExecutedTime = null;
	public BaseCronJob(String cron) {
		setCron(cron);
	}
	
	public String getCron() {
		return cron;
	}

	public void setCron(String cron) {
		this.cron = cron;
	}

	public void scheduedCron() throws Exception {
		Assert.hasText(cron,"cron must be not empty on:" + getClass().getName());
		
		ConcurrentTaskScheduler taskScheduler = new ConcurrentTaskScheduler();
		Runnable task = new Runnable() {
			@Override
			public void run() {
				execute0();
			}
		};
		taskScheduler.schedule(task, new CronTrigger(cron));
		logger.info("scheduled_with_cron:["+cron+"] for \t"+getClass().getSimpleName());
	}
	
	private synchronized void execute0() {
		try {
			logger.info("start_execute_cron_job:"+getClass().getSimpleName());
			execute();
			lastExecutedTime = new Date();
		}catch(Exception e) {
			logger.error("cron_execute_error",e);
		}
	}
	
	public abstract void execute();
	
	public String getJobRemark() {
		return null;
	}
	
	public Date getLastExecutedTime() {
		return lastExecutedTime;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		scheduedCron();
	}
	
}
