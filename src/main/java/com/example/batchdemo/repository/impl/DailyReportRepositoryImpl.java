package com.example.batchdemo.repository.impl;

import com.example.batchdemo.entity.*;
import com.example.batchdemo.repository.DailyReportRepository;
import com.example.batchdemo.service.EntityManagerService;
import org.apache.commons.lang3.StringUtils;
import sql.QuerySql;
import com.opengamma.elsql.ElSql;
import com.opengamma.elsql.ElSqlConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import javax.persistence.Query;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.sql.Timestamp;

import java.time.LocalDate;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class DailyReportRepositoryImpl implements DailyReportRepository {
    private final EntityManagerService entityManager;
    private final ElSql elSqlBundle = ElSql.of(ElSqlConfig.POSTGRES, QuerySql.class);

    //_______________________________________COMPANY_INFORMATION____________________________________________________________
    @Override
    public CompanyInfo getResCompany(int companyId) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_COMPANY_INFO"), CompanyInfo.class);

        query.setParameter("company_id", companyId);

        List<CompanyInfo> companyInfoList = query.getResultList();
        return CollectionUtils.isEmpty(companyInfoList) ? null : companyInfoList.get(0);
    }


//_____________________________________________DATE_____________________________________________________________________

    @Override
    public Timestamp getFrontDate(int companyId) {
        Query query = entityManager.getEntityManager().createNativeQuery(elSqlBundle.getSql("GET_FRONT_DATE"));
        query.setParameter("company_id", companyId);

        List<Timestamp> frontDateList = query.getResultList();
        return CollectionUtils.isEmpty(frontDateList) ? null : frontDateList.get(0);
    }

    @Override
    public Date getReportDate(int companyId, LocalDate frontDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_REPORT_DATE"));
        query.setParameter("company_id", companyId);
        query.setParameter("frontDate", frontDate);

        List<Date> reportDateList = query.getResultList();
        return CollectionUtils.isEmpty(reportDateList) ? null : reportDateList.get(0);

    }

    @Override
    public Date getFrontDateInT2MainAndMarginAccount(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_FRONT_DATE_T2_MAIN_AND_MARGIN_ACCOUNT"));
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        List<Date> frontDateInT2MainAndMarginAccount = query.getResultList();

        return CollectionUtils.isEmpty(frontDateInT2MainAndMarginAccount) ? null : frontDateInT2MainAndMarginAccount.get(0);
    }

    @Override
    public Date getFrontDateInT2NyseAccount(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_FRONT_DATE_NYSE_ACCOUNT"));
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        List<Date> frontDateInT2MainAndMarginAccount = query.getResultList();

        return CollectionUtils.isEmpty(frontDateInT2MainAndMarginAccount) ? null : frontDateInT2MainAndMarginAccount.get(0);
    }

    @Override
    public Date getFrontDateInT2HkseAccount(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_FRONT_DATE_T2_HKSE_ACCOUNT"));
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        List<Date> frontDateInT2MainAndMarginAccount = query.getResultList();

        return CollectionUtils.isEmpty(frontDateInT2MainAndMarginAccount) ? null : frontDateInT2MainAndMarginAccount.get(0);
    }

    @Override
    public Date getTsePrevBizDate(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("TSE_PREV_BIZ_DATE"));
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        List<Date> tsePrevBizDate = query.getResultList();

        return CollectionUtils.isEmpty(tsePrevBizDate) ? null : tsePrevBizDate.get(0);
    }

    @Override
    public Date getNysePrevBizDate(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("NYSE_PREV_BIZ_DATE"));
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        List<Date> nysePrevBizDate = query.getResultList();

        return CollectionUtils.isEmpty(nysePrevBizDate) ? null : nysePrevBizDate.get(0);
    }

    @Override
    public Date getHkPrevBizDate(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("HK_PREV_BIZ_DATE"));
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        List<Date> hkPrevBizDate = query.getResultList();

        return CollectionUtils.isEmpty(hkPrevBizDate) ? null : hkPrevBizDate.get(0);
    }
    //________________________________________________ACCOUNT_ACTIVE________________________________________________________
    @Override
    public List<AccountActive> getAllAccountActive(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_ALL_ACCOUNT_ACTIVE"), AccountActive.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        List<AccountActive> listOfAccountActive = query.getResultList();
        return CollectionUtils.isEmpty(listOfAccountActive) ? null : listOfAccountActive;
    }




    //______________________________________________________________________________________________________________________
    @Override
    public List<TransactionDetails> getTransactionDetailsOfMainAccount(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_TRANSACTION_DETAILS"), TransactionDetails.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);

        return query.getResultList();
    }

    @Override
    public List<TransactionDetails> getTransactionDetailsOfAmericaAccount(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_TRANSACTION_DETAILS_NYSE"), TransactionDetails.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);

        return query.getResultList();
    }

    @Override
    public List<TransactionDetails> getTransactionDetailsOfChinaAccount(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_TRANSACTION_DETAILS_SEHK"), TransactionDetails.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);

        return query.getResultList();
    }

    @Override
    public List<TransactionDetailsMargin> getTransactionDetailsOfMarginAccount(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("TRANSACTION_DETAIL_MARGIN_ACCOUNT"), TransactionDetailsMargin.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }


    @Override
    public List<PaymentAmount> paymentAmountTSE(int companyId, Date preFrontDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("PAYMENT_AMOUNT_TSE"), PaymentAmount.class);
        query.setParameter("company_id", companyId);
        query.setParameter("prevFrontDate", preFrontDate);
        return query.getResultList();
    }

    @Override
    public List<PaymentAmount> paymentAmountNYSE(int companyId, Date NYSEpreFrontDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("PAYMENT_AMOUNT_NYSE"), PaymentAmount.class);
        query.setParameter("company_id", companyId);
        query.setParameter("prevFrontDate", NYSEpreFrontDate);
        return query.getResultList();
    }

    @Override
    public List<PaymentAmount> paymentAmountSEHK(int companyId, Date SEHKpreFrontDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("PAYMENT_AMOUNT_SEHK"), PaymentAmount.class);
        query.setParameter("company_id", companyId);
        query.setParameter("prevFrontDate", SEHKpreFrontDate);
        return query.getResultList();
    }

    @Override
    public BigDecimal getRealizedPlPrevBizDate(String loginId, Date date) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("REALIZED_PL_PREV_BIZ_DATE"));
        query.setParameter("login_id", loginId);
        query.setParameter("date", date);
        List<BigDecimal> realizePlBizDateList=query.getResultList();
        return realizePlBizDateList.stream().findFirst().orElse(BigDecimal.ZERO);
    }

    //___________________________________________________TAX____________________________________________________________
    @Override
    public BigDecimal getWithholdingTaxRate(int companyId) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_WITHHOLDING_TAX_RATE"));
        query.setParameter("company_id", companyId);
        List<BigDecimal> taxRate = query.getResultList();

        return CollectionUtils.isEmpty(taxRate) ? BigDecimal.ZERO : taxRate.get(0);
    }


    //______________________________________________________________________________________________________________
    @Override
    public List<RealizedPL> getPlOfTransactionOnT0(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_PL_OF_TRANSACTION_ON_T0"), RealizedPL.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }


    @Override
    public List<RealizedPL> getPlOfTransactionOnT2(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_PL_OF_TRANSACTION_ON_T2"), RealizedPL.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }

    @Override
    public List<RealizedPL> getPlOfTransactionOnT0NYSE(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_PL_OF_TRANSACTION_ON_T0_NYSE"), RealizedPL.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }

    @Override
    public List<RealizedPL> getPlOfTransactionOnT2NYSE(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_PL_OF_TRANSACTION_ON_T2_NYSE"), RealizedPL.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }

    @Override
    public List<RealizedPL> getPlOfTransactionOnT0SEHK(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_PL_OF_TRANSACTION_ON_T0_SEHK"), RealizedPL.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }

    @Override
    public List<RealizedPL> getPlOfTransactionOnT2SEHK(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_PL_OF_TRANSACTION_ON_T2_SEHK"), RealizedPL.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();

    }

    @Override
    public List<RealizedPL> getPlOfTransactionOnT0Margin(int companyId, Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_PL_OF_TRANSACTION_ON_T0_MARGIN"), RealizedPL.class);
        query.setParameter("company_id", companyId);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }


    //________________________________________________EXCHANGE_RATE_____________________________________________________
    //GET EXCHANGE OF USD/JPY
    @Override
    public BigDecimal getExchangeRateUSDJPY() {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_EXCHANGE_RATE_USD_JPY"));
        List<BigDecimal> exchangeRateUSDJPY = query.getResultList();

        return CollectionUtils.isEmpty(exchangeRateUSDJPY) ? null : exchangeRateUSDJPY.get(0);
    }

    //GET EXCHANGE OF HKD/JPY
    @Override
    public BigDecimal getExchangeRateHKDJPY() {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("GET_EXCHANGE_RATE_HKD_JPY"));

        List<BigDecimal> exchangeRateHKDJPY = query.getResultList();
        return CollectionUtils.isEmpty(exchangeRateHKDJPY) ? null : exchangeRateHKDJPY.get(0);
    }

    @Override
    public List<FeeMargin> getFeeClose(Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("FEE_OF_CLOSE"), FeeMargin.class);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }

    @Override
    public List<FeeMargin> getFeeOpen(Date reportDate) {
        Query query = entityManager.getEntityManager()
                .createNativeQuery(elSqlBundle.getSql("FEE_OF_OPEN"), FeeMargin.class);
        query.setParameter("report_date", reportDate);
        return query.getResultList();
    }
//______________________________________________________________________________________________________________________

}
