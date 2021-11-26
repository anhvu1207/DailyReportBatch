package com.example.batchdemo.beans;

import com.example.batchdemo.entity.TransactionDetails;
import com.example.batchdemo.entity.TransactionDetailsMargin;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class DailyReportBean {
    private String name;
    private String butenCd;
    private String loginId;
    private String businessDate;
    private String titleReportDate;
    private String companyName ;
    private String companyRegistry;
    private String companyAddress;
    private String companyPhone;
    private String companyMail;
    private List<TransactionDetails> JapanStockMarket;
    private List<TransactionDetails> AmericaStockMarket;
    private List<TransactionDetails> ChinaStockMarket;
    private List<TransactionDetailsMargin> MarginDataset;
    private BigDecimal exchangeRateUSDJPY;
    private BigDecimal exchangeRateHKDJPY;
    private BigDecimal taxWithHoldingTSE;
    private BigDecimal taxWithHoldingTSE2;
    private BigDecimal paymentAmountTSE ;
    private BigDecimal paymentAmountTSE2;
    private BigDecimal taxWithHoldingNYSE;
    private BigDecimal taxWithHoldingNYSE2;
    private BigDecimal paymentAmountNYSE;
    private BigDecimal paymentAmountNYSE2;
    private BigDecimal taxWithHoldingSEHK;
    private BigDecimal taxWithHoldingSEHK2;
    private BigDecimal paymentAmountSEHK;
    private BigDecimal paymentAmountSEHK2;
    private BigDecimal taxWithHoldingMargin;
    private BigDecimal taxWithHoldingNyseExchange;
    private BigDecimal taxWithHoldingSehkExchange;
    private BigDecimal paymentAmountMargin;
    private BigDecimal plToThePrevBusinessDay;
    private BigDecimal taxWithHoldingToThePrevDay;
    private BigDecimal realizedPlOnTheDay;
    private BigDecimal taxWithHoldingOnTheDay;
}
