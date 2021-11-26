package com.example.batchdemo.service;

import com.example.batchdemo.entity.CompanyInfo;
import com.example.batchdemo.repository.DailyReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Component
public class GetCompanyInfo {
    private final DailyReportRepository dailyReportRepository;
    public CompanyInfo getCompanyInfo(int companyId){
    return dailyReportRepository.getResCompany(companyId);
    }
}
