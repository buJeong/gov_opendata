package com.bujeong.gov_opendata.schedule;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lombok.extern.slf4j.Slf4j;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@Slf4j
@SpringBootTest
public class JobRegistryTests {
    @Autowired
    private JobRegistry jobRegistry;

    @Autowired
    private JobLauncher jobLauncher;
    
    @Test
    @DisplayName("JobRegistry에 Job이 등록되어 있어야 한다.")
    void test() throws JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException, JobParametersInvalidException {
        try {
            Job job = jobRegistry.getJob("PbroDataUpdateJob");
            JobParameters jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis()).toJobParameters();

            JobExecution jobExecution = jobLauncher.run(job, jobParameters);

            while(jobExecution.isRunning()) {
            	log.debug("isRunning..........");
            }
            
            assertEquals("PbroDataUpdateJob", job.getName());
        } catch (NoSuchJobException e) {
            fail();
        }
    }
}
