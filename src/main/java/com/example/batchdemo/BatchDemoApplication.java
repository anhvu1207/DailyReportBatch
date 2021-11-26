package com.example.batchdemo;

import com.example.batchdemo.entity.AccountActive;
import com.example.batchdemo.repository.DailyReportRepository;
import com.example.batchdemo.repository.StExchangeCalendarRepository;
import com.example.batchdemo.service.DailyReportService;
import com.example.batchdemo.service.GetAllAccountService;
import com.example.batchdemo.service.GetCompanyInfo;
import com.example.batchdemo.service.GetReportDateService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.io.FileNotFoundException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
@RequiredArgsConstructor
@Slf4j
public class BatchDemoApplication implements CommandLineRunner {

    public static void main(String[] args) {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        log.info("{} INFO RP501 [PSP]DailyCustomerStatement START",dtf.format(now));
        new SpringApplicationBuilder(BatchDemoApplication.class)
                .run(args);

    }


    private final DailyReportService dailyReportService;

    //   private final ReaderService stExchangeCalendarService;
    @Override
    public void run(String... args) throws Exception, FileNotFoundException {

        ExecutorService executorService = Executors.newFixedThreadPool(5);
        executorService.execute(new Runnable() {
            @Override
            public void run() {

                dailyReportService.processExport();
            }
        });
//    dailyReportService.processExport();

    }
}