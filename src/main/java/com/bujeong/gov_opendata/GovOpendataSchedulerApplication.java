package com.bujeong.gov_opendata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class GovOpendataSchedulerApplication {

	public static void main(String[] args) {
		SpringApplication.run(GovOpendataSchedulerApplication.class, args);
	}
}
