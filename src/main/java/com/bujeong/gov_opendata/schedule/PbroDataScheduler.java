package com.bujeong.gov_opendata.schedule;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Component
public class PbroDataScheduler {
    private final JobLauncher jobLauncher;
    private final JobRegistry jobRegistry;

    @Scheduled(cron = "0/30 * * * * ?")
    public void runPublicBicycleRentalDataJob() {
        try {
            log.debug("start :: runPublicBicycleRentalDataJob");

            Job job = jobRegistry.getJob("PbroDataUpdateJob");
            JobParametersBuilder jobParameters = new JobParametersBuilder()
                    .addLong("time", System.currentTimeMillis());
            JobExecution jobExecution = jobLauncher.run(job, jobParameters.toJobParameters());

            while(jobExecution.isRunning()) {
            	log.debug("isRunning..........");
            }
        } catch(NoSuchJobException e) {
            log.error(e.getMessage());
        } catch(Exception e) {
            log.debug(e.getMessage());
        }
    }
}
