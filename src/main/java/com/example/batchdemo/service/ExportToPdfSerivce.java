package com.example.batchdemo.service;

import com.example.batchdemo.entity.StExchangeCalendar;
import com.example.batchdemo.repository.StExchangeCalendarRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Component
@RequiredArgsConstructor
@Slf4j
public class ExportToPdfSerivce {
    public void ExportToPdfService(Map<String,Object> params, String accountTradingId , String reportDate){
        try {

            String outputFileLocation = "/home/eco0644_anhvh/Desktop/Export/"+accountTradingId+"_取引報告書_"+reportDate+".pdf";
            String resourceFileLocation="/home/eco0644_anhvh/Desktop/Resource/RPD-501.jrxml";
            //Map to hold Jasper report Parameter

            JasperReport report = JasperCompileManager.compileReport(resourceFileLocation);
            JasperPrint print = JasperFillManager.fillReport(report, params, new JREmptyDataSource());
            JasperExportManager.exportReportToPdfFile(print, outputFileLocation);

            log.info("Successful export to pdf {}",accountTradingId);
        } catch (JRException e) {
            e.printStackTrace();
            log.error("Fail when exporting to pdf with trading account {}", accountTradingId,e);
        }
    }

}
