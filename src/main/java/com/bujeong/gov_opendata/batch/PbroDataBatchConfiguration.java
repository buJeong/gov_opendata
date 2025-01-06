package com.bujeong.gov_opendata.batch;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import com.bujeong.gov_opendata.dto.PbroInfoDTO;
import com.bujeong.gov_opendata.repository.PbroInfoRepository;
import com.bujeong.gov_opendata.repository.entity.PbroInfoEntity;
import com.bujeong.gov_opendata.utils.PbroUtils;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class PbroDataBatchConfiguration {

	private final PbroUtils PbroUtils;
	
	private final ModelMapper modelMapper;
	
    private final JobRepository jobRepository;
    
    private final PlatformTransactionManager opendbTransactionManager;
    
    private final PbroInfoRepository pbroInfoRepository;

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
    		
    		list.stream().forEach(item -> pbroInfoRepository.save(modelMapper.map(item, PbroInfoEntity.class)));

    		return RepeatStatus.FINISHED;
    	});
    }

}
