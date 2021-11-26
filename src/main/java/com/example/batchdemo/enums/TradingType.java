package com.example.batchdemo.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TradingType {
    MAIN_ACCOUNT("11"),
    NYSE_ACCOUNT("12"),
    HKSE_ACCOUNT("13"),
    MARGIN_ACCOUNT("14");

    private String code;
}
