package com.example.batchdemo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;

@Entity
@Data
public class FeeMargin {
@Column(name ="fee_trading")
private BigDecimal fee;
@Id
@Column(name = "trading_account_id")
private String loginId;
}
