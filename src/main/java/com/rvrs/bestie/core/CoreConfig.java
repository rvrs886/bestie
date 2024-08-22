package com.rvrs.bestie.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
public class CoreConfig {

	@Value("${scheduledEvents.thread.pool:2}")
	private int scheduledEventsThreadPoolSize;

	@Bean
	public ExecutorService scheduledEventsExecutorService() {
		return Executors.newFixedThreadPool(scheduledEventsThreadPoolSize);
	}

}
