package com.example.batchdemo.service;

import com.example.batchdemo.repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

@RequiredArgsConstructor
@Slf4j
@Component
public class GetReportDateService {
    private final DailyReportRepository dailyReportRepository;

    public Date getReportDate(int companyId) {
        Timestamp frontDate = dailyReportRepository.getFrontDate(companyId);
        if (Objects.isNull(frontDate))
            return null;

        return dailyReportRepository.getReportDate(companyId, frontDate.toLocalDateTime().toLocalDate());
    }
}
