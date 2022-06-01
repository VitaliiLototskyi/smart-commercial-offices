package com.microservices.analyzerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableMongoRepositories("com.microservices.analyzerservice.repository.raw")
@EnableScheduling
public class AnalyzerServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnalyzerServiceApplication.class, args);
	}

}
