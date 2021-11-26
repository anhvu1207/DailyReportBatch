package com.example.batchdemo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class TransactionDetailsMargin {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "ticker_code")
    private String tickerCode;

    @Column(name = "ticker_name")
    private String tickerName;

    @Column(name = "side")
    private String side;

    @Column(name = "order_qty")
    private BigDecimal orderQty;

    @Column(name = "trade_date")
    private String tradeDate;

    @Column(name = "settle_date")
    private String settleDate;

    @Column(name = "order_price")
    private BigDecimal orderPrice;

    @Column(name = "fee_trading")
    private BigDecimal feeTrading;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "fee_loan")
    private BigDecimal feeLoan;

    @Column(name = "stock_lending_fee")
    private BigDecimal stockLendingFee;

    @Column(name = "transfer_fee")
    private BigDecimal transferFee;

    @Column(name = "adjust_dividend_cash")
    private  BigDecimal adjustDividendCash;

    @Column(name = "position_id")
    private String positionId;

    @Column(name = "trading_account_id")
    private String tradingAcct;
    @Column(name = "amount")
    private BigDecimal amount;
}
