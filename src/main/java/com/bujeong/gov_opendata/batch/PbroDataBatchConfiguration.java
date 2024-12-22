package com.bujeong.gov_opendata.batch;

import java.util.List;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.bujeong.gov_opendata.config.TransactionManagerConfig;
import com.bujeong.gov_opendata.dto.PbroInfoDTO;
import com.bujeong.gov_opendata.utils.PbroUtils;

import jakarta.persistence.EntityManagerFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
public class PbroDataBatchConfiguration {

	@Autowired
	private PbroUtils PbroUtils;
	
    private final JobRepository jobRepository;
    private final PlatformTransactionManager opendbTransactionManager;

    public PbroDataBatchConfiguration(JobRepository jobRepository
    									, @Qualifier(TransactionManagerConfig.OPENDB_TRANSACTION_MANAGER) PlatformTransactionManager transactionManager) {
    	this.jobRepository = jobRepository;
    	this.opendbTransactionManager = transactionManager;
    }

    @Bean
    public Job PbroDataUpdateJob() {
        return new JobBuilder("PbroDataUpdateJob", jobRepository)
                .start(PbroDataUpdateStep())
                .build();
    }

    @Bean
    public Step PbroDataUpdateStep() {
        return new StepBuilder("PbroDataUpdateStep", jobRepository)
                .tasklet(tasklet(), opendbTransactionManager)
                .build();
    }
    
    @Bean
    public Tasklet tasklet() {
    	return ((contribution, chunkContext) -> {
    		List<PbroInfoDTO> list = PbroUtils.getPbroInfoAsDtoList();
    		
    		for (PbroInfoDTO dto : list) {
    			log.debug("PbroInfoDTO :: {}", dto);
    		}
    		
    		return RepeatStatus.FINISHED;
    	});
    }

}
