package com.example.batchdemo.service;

import com.example.batchdemo.entity.*;
import com.example.batchdemo.enums.TradingType;

import java.math.BigDecimal;
import java.util.List;

public interface ProcessService {
    List<TransactionDetails> getTransactionDetails(String loginId , List<TransactionDetails> list);

    List<TransactionDetailsMargin> getTransactionDetailsOfMargin(String loginId , List<TransactionDetailsMargin> lsit);
    BigDecimal getRealizedPL(String loginId , List<RealizedPL> list);

    BigDecimal getWithHoldingTax(BigDecimal taxRate ,String loginId , List<RealizedPL> list);

    BigDecimal totalBuyPayment(String loginId , List<PaymentAmount> list);

    BigDecimal totalSellPayment(String loginId , List<PaymentAmount> list);

    BigDecimal sumSell(List<TransactionDetails> list);

    BigDecimal sumBuy(List<TransactionDetails> list);

    BigDecimal paymentAmount(String accountCategory,BigDecimal sell ,BigDecimal buy, BigDecimal taxWithHolding);

    String getLoginIdForEachStockMarket(String loginId ,TradingType tradingType );

    BigDecimal getFeeTrading(String loginId , List<FeeMargin> list);
}
