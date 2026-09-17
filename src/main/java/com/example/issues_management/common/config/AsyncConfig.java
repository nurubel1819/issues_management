package com.example.issues_management.common.config;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;

import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;


@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    private static final Logger log = LoggerFactory.getLogger(AsyncConfig.class);

    @Bean("virtualTaskExecutor")
    public Executor virtualTaskExecutor() {

        return Executors.newVirtualThreadPerTaskExecutor();
    }


    @Bean("cpuTaskExecutor")
    public Executor cpuTaskExecutor() {

        return Executors.newFixedThreadPool(
                Runtime.getRuntime()
                        .availableProcessors()
        );
    }

    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (Throwable ex, Method method, Object... params) -> {
            log.error("Async method '{}' threw exception: {}", method.getName(), ex.getMessage(), ex);
            // if you want retry queue / alert / dead-letter table-in here
        };
    }
}
