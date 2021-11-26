package com.example.batchdemo.service.impl;

import com.example.batchdemo.entity.*;
import com.example.batchdemo.enums.TradingType;
import com.example.batchdemo.service.ProcessService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProcessServiceImpl implements ProcessService {
    @Override
    public List<TransactionDetails> getTransactionDetails(String loginId, List<TransactionDetails> list) {
        List<TransactionDetails> dataSource = list.stream()
                .filter(i -> loginId.equals(i.getTradingAcct()))
                .collect(Collectors.toList());
        return dataSource;
    }

    @Override
    public List<TransactionDetailsMargin> getTransactionDetailsOfMargin(String loginId, List<TransactionDetailsMargin> lsit) {
        return lsit.stream()
                .filter(i ->loginId.equals(i.getTradingAcct()))
                .collect(Collectors.toList());
    }

    @Override
    public BigDecimal getRealizedPL(String loginId, List<RealizedPL> list) {
        BigDecimal realizedPL = list.stream()
                .filter((i -> loginId.equals(i.getTradingAccount())))
                .map(RealizedPL::getRealizedPL)
                .findFirst().orElse(BigDecimal.ZERO);
        return realizedPL;
    }

    @Override
    public BigDecimal getWithHoldingTax(BigDecimal taxRate, String loginId, List<RealizedPL> list) {
        BigDecimal realizedPl = getRealizedPL(loginId, list);
        if (realizedPl.compareTo(BigDecimal.ZERO) < 0)
            return BigDecimal.ZERO;
        return realizedPl.multiply(taxRate.divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP));

    }

    @Override
    public BigDecimal totalBuyPayment(String loginId, List<PaymentAmount> list) {
        BigDecimal totalBuyPayment = list.stream()
                .filter(i -> loginId.equals(i.getLoginId()))
                .map(PaymentAmount::getTotalBuy)
                .findFirst().orElse(BigDecimal.ZERO);
        return totalBuyPayment;
    }

    @Override
    public BigDecimal totalSellPayment(String loginId, List<PaymentAmount> list) {
        BigDecimal totalSellPayment = list.stream()
                .filter(i -> loginId.equals(i.getLoginId()))
                .map(PaymentAmount::getTotalSell)
                .findFirst().orElse(BigDecimal.ZERO);
        return totalSellPayment;
    }

    @Override
    public BigDecimal sumSell(List<TransactionDetails> list) {
        return list.stream()
                .filter(i -> i.getSide().equals("2"))
                .map(TransactionDetails::getStatementAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

    }

    @Override
    public BigDecimal sumBuy(List<TransactionDetails> list) {
        return list.stream()
                .filter(i -> i.getSide().equals("1"))
                .map(TransactionDetails::getStatementAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Override
    public BigDecimal paymentAmount(String accountCategory, BigDecimal sell ,BigDecimal buy ,BigDecimal taxWithHolding) {
        if (StringUtils.isBlank(accountCategory))
            return BigDecimal.ZERO;

        if (accountCategory.equals("10") || accountCategory.equals("21")) {
            return sell.subtract(buy);
        }
            return (sell.subtract(buy)).subtract(taxWithHolding);
    }

    @Override
    public String getLoginIdForEachStockMarket(String loginId, TradingType tradingType) {

        return loginId.substring(0,8).concat(tradingType.getCode());
    }

    @Override
    public BigDecimal getFeeTrading(String loginId, List<FeeMargin> list) {
        BigDecimal feeTrading = list.stream()
                .filter(i -> loginId.equals(i.getLoginId()))
                .map(FeeMargin::getFee)
                .findFirst().orElse(BigDecimal.ZERO);
        return feeTrading;
    }


}
