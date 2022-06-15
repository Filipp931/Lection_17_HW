package com.throttler.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@Configuration
@EnableAsync
public class SpringAsyncConfig implements AsyncConfigurer {
    @Value("${numberOfThreads}")
    private int numberOfThreads;

    @Override
    public Executor getAsyncExecutor() {
        return  Executors.newFixedThreadPool(numberOfThreads);
    }
}
