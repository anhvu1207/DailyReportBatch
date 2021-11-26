package com.example.batchdemo.service;

import com.example.batchdemo.beans.DailyReportBean;
import com.example.batchdemo.constant.Constant;
import com.example.batchdemo.converter.DailyReportConverter;
import com.example.batchdemo.entity.*;
import com.example.batchdemo.enums.TradingType;
import com.example.batchdemo.repository.DailyReportRepository;
import com.example.batchdemo.resources.CommonResource;
import com.example.batchdemo.utils.MapperUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Slf4j
@Component
public class DailyReportService {

    private final DailyReportRepository dailyReportRepository;
    private final CommonResource commonResource;
    private final GetReportDateService getReportDateService;
    private final ProcessService processService;
    private final GetCompanyInfo getCompanyInfo;
    private final ExportToPdfSerivce exportToPdfSerivce;

    private final Constant constant;


    public void processExport() {
        String reportTitle =Constant.REPORT_TITLE;
        log.info("Get psp report title template information");

        //Get company id
        int companyId = commonResource.getCompanyId();

        //Get Company Information
        CompanyInfo companyInfo = getCompanyInfo.getCompanyInfo(companyId);
        log.info("Get company info");
        //Get company address
        String companyAddress = getCompanyAddress(companyInfo);

        //Get Tax rate
        BigDecimal taxRate = dailyReportRepository.getWithholdingTaxRate(companyId);


        //_________DATE_____

        //Get report_date
        Date reportDate = getReportDateService.getReportDate(companyId);
        if (Objects.isNull(reportDate)) {
            log.warn("Stop batch because not exist front date in st_app_date or st_exchange_calendar!");
            return;
        }

        Date prevFrontDate = dailyReportRepository.getFrontDateInT2MainAndMarginAccount(companyId, reportDate);
        Date nysePrevFrontDate = dailyReportRepository.getFrontDateInT2NyseAccount(companyId, reportDate);
        Date hkPrevFrontDate = dailyReportRepository.getFrontDateInT2HkseAccount(companyId, reportDate);

        Date tsePrevBizDate = dailyReportRepository.getTsePrevBizDate(companyId, reportDate);
        Date nysePrevBizDate = dailyReportRepository.getNysePrevBizDate(companyId, reportDate);
        Date sehkPrevBizDate = dailyReportRepository.getHkPrevBizDate(companyId, reportDate);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();


        //______EXCHANGE_RATE_____
        BigDecimal exchangeRateUSDJPY = dailyReportRepository.getExchangeRateUSDJPY();
        BigDecimal exchangeRateHKDJPY = dailyReportRepository.getExchangeRateHKDJPY();

        //_______TRANSACTION_FOR_EACH_STOCK______
        List<TransactionDetails> transactionDetailsList = dailyReportRepository.getTransactionDetailsOfMainAccount(companyId, reportDate);
        List<TransactionDetails> transactionDetailsListNYSE = dailyReportRepository.getTransactionDetailsOfAmericaAccount(companyId, reportDate);
        List<TransactionDetails> transactionDetailsListSEHK = dailyReportRepository.getTransactionDetailsOfChinaAccount(companyId, reportDate);
        List<TransactionDetailsMargin> transactionDetailsMarginList = dailyReportRepository.getTransactionDetailsOfMarginAccount(companyId, reportDate);


        //_________REALIZED_PL_T0_______
        List<RealizedPL> TSErealizedPlList = dailyReportRepository.getPlOfTransactionOnT0(companyId, reportDate);
        List<RealizedPL> NYSErealizedPlList = dailyReportRepository.getPlOfTransactionOnT0NYSE(companyId, reportDate);
        List<RealizedPL> SEHKrealizedPlList = dailyReportRepository.getPlOfTransactionOnT0SEHK(companyId, reportDate);
        List<RealizedPL> marginRealizedPlList = dailyReportRepository.getPlOfTransactionOnT0Margin(companyId, reportDate);


        //_______REALIZED_PL_T2_______
        List<RealizedPL> TSErealizedPlList2 = dailyReportRepository.getPlOfTransactionOnT2(companyId, reportDate);
        List<RealizedPL> NYSErealizedPlList2 = dailyReportRepository.getPlOfTransactionOnT2NYSE(companyId, reportDate);
        List<RealizedPL> SEHKrealizedPlList2 = dailyReportRepository.getPlOfTransactionOnT2SEHK(companyId, reportDate);


        //_______PAYMENT_AMOUNT_________

        List<PaymentAmount> paymentAmountListTSE = dailyReportRepository.paymentAmountTSE(companyId, prevFrontDate);
        List<PaymentAmount> paymentAmountListNYSE = dailyReportRepository.paymentAmountNYSE(companyId, nysePrevFrontDate);
        List<PaymentAmount> paymentAmountListSEHK = dailyReportRepository.paymentAmountSEHK(companyId, hkPrevFrontDate);

        //________FEE_OF_CLOSE_AND_OPEN_POSITION__________

        List<FeeMargin> feeOfCloseList = dailyReportRepository.getFeeClose(reportDate);
        List<FeeMargin> feeOfOpenList = dailyReportRepository.getFeeOpen(reportDate);


        //1.Get All Active Account
        List<AccountActive> accountActiveList = dailyReportRepository.getAllAccountActive(companyId, reportDate);
        log.info("Number of generating reports:{}",accountActiveList.size());
        for (AccountActive account : accountActiveList) {
            //Account id for each stock
            String mainAcct = account.getLoginId();
            String nyseAccount = processService.getLoginIdForEachStockMarket(mainAcct, TradingType.NYSE_ACCOUNT);
            String sehkAccount = processService.getLoginIdForEachStockMarket(mainAcct, TradingType.HKSE_ACCOUNT);
            String marginAccount = processService.getLoginIdForEachStockMarket(mainAcct, TradingType.MARGIN_ACCOUNT);

            //Get transactions list for each stock
            List<TransactionDetails> mainDataSource = processService.getTransactionDetails(mainAcct, transactionDetailsList);
            List<TransactionDetails> americaDataSource = processService.getTransactionDetails(nyseAccount, transactionDetailsListNYSE);
            List<TransactionDetails> chinaDataSource = processService.getTransactionDetails(sehkAccount, transactionDetailsListSEHK);
            List<TransactionDetailsMargin> marginDataset = processService.getTransactionDetailsOfMargin(marginAccount, transactionDetailsMarginList);

            //_________________TSE


            BigDecimal taxWithHoldingTSE = processService.getWithHoldingTax(taxRate, mainAcct, TSErealizedPlList);
            BigDecimal taxWithHoldingTSE2 = processService.getWithHoldingTax(taxRate, mainAcct, TSErealizedPlList2);
            BigDecimal sumBuyTest=processService.sumBuy(mainDataSource);
            BigDecimal paymentAmountTSE = processService.paymentAmount(
                    account.getStAccountCategory(),
                    processService.sumSell(mainDataSource),
                    processService.sumBuy(mainDataSource),
                    taxWithHoldingTSE);
            BigDecimal paymentAmountTSE2 = processService.paymentAmount(account.getStAccountCategory(),
                    processService.totalSellPayment(mainAcct, paymentAmountListTSE),
                    processService.totalBuyPayment(mainAcct, paymentAmountListTSE),
                    taxWithHoldingTSE2);

            //_____________NYSE


            BigDecimal taxWithHoldingNYSE = processService.getWithHoldingTax(taxRate, nyseAccount, NYSErealizedPlList);
            BigDecimal taxWithHoldingNyseExchange = taxWithHoldingNYSE.multiply(exchangeRateUSDJPY);
            BigDecimal taxWithHoldingNYSE2 = processService.getWithHoldingTax(taxRate, nyseAccount, NYSErealizedPlList2);
            BigDecimal paymentAmountNYSE = processService.paymentAmount(
                    account.getStAccountCategory(),
                    processService.sumSell(americaDataSource),
                    processService.sumBuy(americaDataSource),
                    taxWithHoldingNYSE);
            BigDecimal paymentAmountNYSE2 = processService.paymentAmount(
                    account.getStAccountCategory(),
                    processService.totalSellPayment(nyseAccount, paymentAmountListNYSE),
                    processService.totalBuyPayment(nyseAccount, paymentAmountListNYSE),
                    taxWithHoldingNYSE2);

            //________________SEHK


            BigDecimal taxWithHoldingSEHK = processService.getWithHoldingTax(taxRate, sehkAccount, SEHKrealizedPlList);
            BigDecimal taxWithHoldingSehkExchange = taxWithHoldingSEHK.multiply(exchangeRateHKDJPY);
            BigDecimal taxWithHoldingSEHK2 = processService.getWithHoldingTax(taxRate, sehkAccount, SEHKrealizedPlList2);
            BigDecimal paymentAmountSEHK = processService.paymentAmount(
                    account.getStAccountCategory(),
                    processService.sumSell(chinaDataSource),
                    processService.sumBuy(chinaDataSource),
                    taxWithHoldingSEHK);
            BigDecimal paymentAmountSEHK2 = processService.paymentAmount(
                    account.getStAccountCategory(),
                    processService.totalSellPayment(sehkAccount, paymentAmountListSEHK),
                    processService.totalBuyPayment(sehkAccount, paymentAmountListSEHK),
                    taxWithHoldingSEHK2);


            //_____________MARGIN_ACCOUNT


            BigDecimal taxWithHoldingMargin = processService.getWithHoldingTax(taxRate, marginAccount, marginRealizedPlList);
            BigDecimal feeOfClose = processService.getFeeTrading(marginAccount, feeOfCloseList);
            BigDecimal feeOfOpen = processService.getFeeTrading(marginAccount, feeOfOpenList);
            BigDecimal paymentAmountMargin = processService.getRealizedPL(marginAccount, marginRealizedPlList).
                    subtract(feeOfClose).
                    subtract(feeOfOpen);


            //______________________________________REPORT_FOOTER_______________________________________________________
            BigDecimal realizedPlMainBizDate = dailyReportRepository.getRealizedPlPrevBizDate(mainAcct, tsePrevBizDate);
            BigDecimal realizedPlMargin = dailyReportRepository.getRealizedPlPrevBizDate(marginAccount, tsePrevBizDate);
            BigDecimal realizedPlNYSEBizDate = dailyReportRepository.getRealizedPlPrevBizDate(nyseAccount, nysePrevBizDate);
            BigDecimal realizedPlSEHKBizDate = dailyReportRepository.getRealizedPlPrevBizDate(sehkAccount, sehkPrevBizDate);
            BigDecimal plToThePrevBusinessDay = realizedPlMainBizDate.add(realizedPlMargin).add(realizedPlNYSEBizDate).add(realizedPlSEHKBizDate);
            BigDecimal taxWithHoldingToThePrevDay = plToThePrevBusinessDay.multiply(taxRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));
            BigDecimal taxWithHoldingOnTheDay = taxWithHoldingTSE.add(taxWithHoldingNYSE).add(taxWithHoldingSEHK).add(taxWithHoldingMargin);
            BigDecimal realizedPlOnTheDay = processService.getRealizedPL(mainAcct, TSErealizedPlList).
                    add(processService.getRealizedPL(nyseAccount, NYSErealizedPlList)).
                    add(processService.getRealizedPL(sehkAccount, SEHKrealizedPlList)).
                    add(processService.getRealizedPL(marginAccount, marginRealizedPlList));


            //_________________________________________________________EXPORT____________

            DailyReportBean bean = DailyReportConverter.toDailyReportBean(
                    companyInfo,
                    account,
                    companyAddress,
                    mainDataSource,
                    americaDataSource,
                    chinaDataSource,
                    reportDate.toString(),
                    dtf.format(now).toString(),
                    exchangeRateUSDJPY,
                    exchangeRateHKDJPY,
                    taxWithHoldingTSE,
                    taxWithHoldingTSE2,
                    paymentAmountTSE,
                    paymentAmountTSE2,
                    taxWithHoldingNYSE,
                    taxWithHoldingNYSE2,
                    paymentAmountNYSE,
                    paymentAmountNYSE2,
                    taxWithHoldingSEHK,
                    taxWithHoldingSEHK2,
                    paymentAmountSEHK,
                    paymentAmountSEHK2,
                    taxWithHoldingNyseExchange,
                    taxWithHoldingSehkExchange,
                    marginDataset,
                    taxWithHoldingMargin,
                    paymentAmountMargin,
                    plToThePrevBusinessDay,
                    taxWithHoldingToThePrevDay,
                    realizedPlOnTheDay,
                    taxWithHoldingOnTheDay
                    );
            Map<String, Object> params = MapperUtil.toObjectMap(bean);
            String reportDateFormat = reportDate.toLocalDate().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
            exportToPdfSerivce.ExportToPdfService(params, account.getLoginId(), reportDateFormat);
        }
    }

    private String getCompanyAddress(CompanyInfo companyInfo) {
        StringBuilder address = new StringBuilder();
        if (StringUtils.isNoneBlank(companyInfo.getCity()))
            address.append(companyInfo.getCity());

        if (StringUtils.isNoneBlank(companyInfo.getStreet()))
            address.append(companyInfo.getStreet());
        if (StringUtils.isNoneBlank(companyInfo.getStreet2()))
            address.append(companyInfo.getStreet2());
        if (StringUtils.isNoneBlank(companyInfo.getStreet3()))
            address.append(companyInfo.getStreet3());
        return address.toString();
    }

}
