package com.example.batchdemo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
public class TransactionDetails {
    @Id
    @Column(name = "order_id")
    private String orderId;

    @Column(name = "trade_date")
    private String tradeDate;

    @Column(name = "settle_date")
    private String settleDate;

    @Column(name = "ticker_code")
    private String tickerCode;

    @Column(name = "ticker_name")
    private String tickerName;

    @Column(name = "side")
    private String side;

    @Column(name = "account_category")
    private String accountCategory;

    @Column(name = "order_qty")
    private Integer orderQty;

    @Column(name = "order_price")
    private BigDecimal orderPrice;

    @Column(name = "fee_trading")
    private BigDecimal feeTrading;

    @Column(name = "tax")
    private BigDecimal tax;

    @Column(name = "statement_amount")
   private BigDecimal statementAmount;

    @Column(name = "trading_account_id")
    private String tradingAcct;
}
