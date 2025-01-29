package com.victor.ray.event.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
public class AsyncConfig {
    @Bean(name = "taskExecutor")
    public ThreadPoolTaskExecutor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(50);         // Adjust based on load
        executor.setMaxPoolSize(200);         // Scaled to handle peak load
        executor.setQueueCapacity(500);       // Buffering capacity for requests
        executor.setThreadNamePrefix("Async-");
        executor.initialize();
        return executor;
    }
}
