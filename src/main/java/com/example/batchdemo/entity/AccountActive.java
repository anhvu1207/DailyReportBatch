package com.example.batchdemo.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
@Data
@Entity
public class AccountActive {
    @Id
    @Column(name = "login_id")
    private String loginId;
    @Column(name = "name")
    private String name;
    @Column(name = "buten_cd")
    private String butenCd;
    @Column(name = "base_currency_cd")
    private String baseCurrencyCd;
    @Column(name = "st_account_category")
    private String stAccountCategory;

}
