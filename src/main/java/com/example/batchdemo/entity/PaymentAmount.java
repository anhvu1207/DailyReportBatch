package com.example.batchdemo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Data
@Entity
public class PaymentAmount {
    @Id
    @Column(name = "trading_account_id")
    private String loginId;
    @Column(name = "total_buy_executed_amt")
    private BigDecimal totalBuy;
    @Column(name = "total_sell_executed_amt")
    private BigDecimal totalSell;
}
