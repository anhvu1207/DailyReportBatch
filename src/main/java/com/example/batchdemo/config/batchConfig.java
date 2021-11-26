package com.example.batchdemo.config;

import com.example.batchdemo.service.TaskletService;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;

@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class batchConfig {
    private final StepBuilderFactory stepBuilderFactory;
    private final JobBuilderFactory jobBuilderFactory;
    private final TaskletService taskletService;

    @Bean
    public Job createJob(){
        return jobBuilderFactory.get("createJob")
                .incrementer(new RunIdIncrementer())
                .start(createStep())
                .build();
    }
    @Bean
    public Step createStep(){
        TaskletStep createStep = stepBuilderFactory
                .get("createStep")
                .tasklet(taskletService)
                .build();
        return createStep;
    }

}
