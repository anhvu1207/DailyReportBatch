package com.example.batchdemo.converter;

import com.example.batchdemo.beans.DailyReportBean;
import com.example.batchdemo.entity.AccountActive;
import com.example.batchdemo.entity.CompanyInfo;
import com.example.batchdemo.entity.TransactionDetails;
import com.example.batchdemo.entity.TransactionDetailsMargin;
import lombok.RequiredArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@RequiredArgsConstructor
public class DailyReportConverter {


    public static DailyReportBean toDailyReportBean(CompanyInfo companyInfo, AccountActive accountActive, String companyAddress, List<TransactionDetails> mainDataset, List<TransactionDetails> americaDataset, List<TransactionDetails> chinaDataset, String reportDate, String titleReportDate, BigDecimal exchangeRateUSDJPY , BigDecimal exchangeRateHKDJPY
            , BigDecimal taxWithHoldingTSE, BigDecimal taxWithHoldingTSE2,
                                                    BigDecimal paymentAmountTSE, BigDecimal paymentAmountTSE2,
                                                    BigDecimal taxWithHoldingNYSE, BigDecimal taxWithHoldingNYSE2, BigDecimal paymentAmountNYSE,
                                                    BigDecimal paymentAmountNYSE2, BigDecimal taxWithHoldingSEHK, BigDecimal taxWithHoldingSEHK2,
                                                    BigDecimal paymentAmountSEHK, BigDecimal paymentAmountSEHK2, BigDecimal taxWithHoldingNyseExchange,
                                                    BigDecimal taxWithHoldingSehkExchange, List<TransactionDetailsMargin> marginDataset,BigDecimal taxWithHoldingMargin ,
                                                    BigDecimal paymentAmountMargin ,
                                                    BigDecimal plToThePrevBusinessDay,
                                                    BigDecimal taxWithHoldingToThePrevDay,
                                                    BigDecimal realizedPlOnTheDay,
                                                    BigDecimal taxWithHoldingOnTheDay) {
        DailyReportBean bean =new DailyReportBean();
        bean.setName(accountActive.getName());
        bean.setButenCd(accountActive.getButenCd());
        bean.setLoginId(accountActive.getLoginId());
        bean.setBusinessDate(reportDate);
        bean.setTitleReportDate(titleReportDate);
        bean.setCompanyName(companyInfo.getName());
        bean.setCompanyRegistry(companyInfo.getCompanyRegistry());
        bean.setCompanyAddress(companyAddress);
        bean.setCompanyMail(companyInfo.getEmail());
        bean.setCompanyPhone(companyInfo.getPhone());
        bean.setJapanStockMarket(mainDataset);
        bean.setAmericaStockMarket(americaDataset);
        bean.setChinaStockMarket(chinaDataset);
        bean.setExchangeRateUSDJPY(exchangeRateUSDJPY);
        bean.setExchangeRateHKDJPY(exchangeRateHKDJPY);
        bean.setTaxWithHoldingTSE(taxWithHoldingTSE);
        bean.setTaxWithHoldingTSE2(taxWithHoldingTSE2);
        bean.setPaymentAmountTSE(paymentAmountTSE);
        bean.setPaymentAmountTSE2(paymentAmountTSE2);
        bean.setTaxWithHoldingNYSE(taxWithHoldingNYSE);
        bean.setTaxWithHoldingNYSE2(taxWithHoldingNYSE2);
        bean.setPaymentAmountNYSE(paymentAmountNYSE);
        bean.setPaymentAmountNYSE2(paymentAmountNYSE2);
        bean.setTaxWithHoldingSEHK(taxWithHoldingSEHK);
        bean.setTaxWithHoldingSEHK2(taxWithHoldingSEHK2);
        bean.setPaymentAmountSEHK(paymentAmountSEHK);
        bean.setPaymentAmountSEHK2(paymentAmountSEHK2);
        bean.setTaxWithHoldingNyseExchange(taxWithHoldingNyseExchange);
        bean.setTaxWithHoldingSehkExchange(taxWithHoldingSehkExchange);
        bean.setMarginDataset(marginDataset);
        bean.setTaxWithHoldingMargin(taxWithHoldingMargin);
        bean.setPaymentAmountMargin(paymentAmountMargin);
        bean.setPlToThePrevBusinessDay(plToThePrevBusinessDay);
        bean.setTaxWithHoldingToThePrevDay(taxWithHoldingToThePrevDay);
        bean.setRealizedPlOnTheDay(realizedPlOnTheDay);
        bean.setTaxWithHoldingOnTheDay(taxWithHoldingOnTheDay);
        return bean;
    }


}
