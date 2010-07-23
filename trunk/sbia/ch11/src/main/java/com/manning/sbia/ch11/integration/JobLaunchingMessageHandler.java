/**
 * 
 */
package com.manning.sbia.ch11.integration;

import java.util.Map;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobLocator;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;

/**
 * @author acogoluegnes
 *
 */
public class JobLaunchingMessageHandler {
	
	private JobLocator jobLocator;
	
	private JobLauncher jobLauncher;	

	public JobLaunchingMessageHandler(JobLocator jobLocator,
			JobLauncher jobLauncher) {
		super();
		this.jobLocator = jobLocator;
		this.jobLauncher = jobLauncher;
	}

	public JobExecution launch(JobLaunchRequest request)
			throws JobExecutionAlreadyRunningException, JobRestartException,
			JobInstanceAlreadyCompleteException, JobParametersInvalidException,
			NoSuchJobException {
		Job job = jobLocator.getJob(request.getJobName());
		JobParametersBuilder builder = new JobParametersBuilder();
		for(Map.Entry<String,String> entry : request.getJobParameters().entrySet()) {
			builder.addString(entry.getKey(), entry.getValue());
		}
		return jobLauncher.run(job, builder.toJobParameters());
	}
	
}
