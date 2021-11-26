package com.example.batchdemo.repository;

import com.example.batchdemo.entity.*;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;

public interface DailyReportRepository {
    CompanyInfo getResCompany(int companyId);

    //_________________________________________________DATE_____________________________________________________________
    Timestamp getFrontDate(int companyId);

    Date getReportDate(int companyId, LocalDate frontDate);

    Date getFrontDateInT2MainAndMarginAccount(int companyId, Date reportDate);

    Date getFrontDateInT2NyseAccount(int companyId, Date reportDate);

    Date getFrontDateInT2HkseAccount(int companyId, Date reportDate);

    Date getTsePrevBizDate(int companyId , Date reportDate);
    Date getNysePrevBizDate(int companyId , Date reportDate);
    Date getHkPrevBizDate(int companyId, Date reportDate);


    //_________________________________________TRANSACTION_DETAILS______________________________________________________
    List<AccountActive> getAllAccountActive(int companyId, Date reportDate);

    List<TransactionDetails> getTransactionDetailsOfMainAccount(int companyId, Date reportDate);

    List<TransactionDetails> getTransactionDetailsOfAmericaAccount(int companyId, Date reportDate);

    List<TransactionDetails> getTransactionDetailsOfChinaAccount(int companyId, Date reportDate);

    List<TransactionDetailsMargin> getTransactionDetailsOfMarginAccount(int companyId, Date reportDate);

    //_______________________________________________________TAX________________________________________________________

    BigDecimal getWithholdingTaxRate(int companyId);


    //______________________________________________________________________________________________________________________
    //GET PL OF TRANSACTION
    List<RealizedPL> getPlOfTransactionOnT0(int companyId, Date reportDate);

    List<RealizedPL> getPlOfTransactionOnT2(int companyId, Date reportDate);

    List<RealizedPL> getPlOfTransactionOnT0NYSE(int companyId, Date reportDate);

    List<RealizedPL> getPlOfTransactionOnT2NYSE(int companyId, Date reportDate);

    List<RealizedPL> getPlOfTransactionOnT0SEHK(int companyId, Date reportDate);

    List<RealizedPL> getPlOfTransactionOnT2SEHK(int companyId, Date reportDate);

    List<RealizedPL> getPlOfTransactionOnT0Margin(int companyId , Date reportDate);
    List<PaymentAmount> paymentAmountTSE(int companyId, Date preFrontDate);

    List<PaymentAmount> paymentAmountNYSE(int companyId, Date NYSEpreFrontDate);

    List<PaymentAmount> paymentAmountSEHK(int companyId, Date SEHKpreFrontDate);

    BigDecimal getRealizedPlPrevBizDate(String loginId , Date date);

    //______________________________________________EXCHANGE_RATE___________________________________________________________
    BigDecimal getExchangeRateUSDJPY();

    BigDecimal getExchangeRateHKDJPY();
//______________________________________________________________________________________________________________________

    List<FeeMargin> getFeeClose(Date reportDate);

    List<FeeMargin> getFeeOpen(Date reportDate);


}
