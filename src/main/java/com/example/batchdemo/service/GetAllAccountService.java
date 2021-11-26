package com.example.batchdemo.service;

import com.example.batchdemo.entity.AccountActive;
import com.example.batchdemo.repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

@RequiredArgsConstructor
@Component
public class GetAllAccountService {
    private final GetReportDateService getReportDateService;
    private final DailyReportRepository dailyReportRepository;


    public List<AccountActive> getAllAccountActive(int companyId) {
        Date reportDate = getReportDateService.getReportDate(companyId);
        return dailyReportRepository.getAllAccountActive(companyId,reportDate);
    }
}
