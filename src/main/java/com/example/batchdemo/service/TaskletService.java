package com.example.batchdemo.service;

import com.example.batchdemo.entity.StExchangeCalendar;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Component
public class TaskletService implements Tasklet {
    private final DailyReportService dailyReportService;
    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext)  {
      dailyReportService.processExport();

         return RepeatStatus.FINISHED;
    }
}
