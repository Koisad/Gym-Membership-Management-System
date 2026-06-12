package com.sii.gym.dto;

import com.sii.gym.model.enums.Currency;

import java.math.BigDecimal;

public record RevenueReportResponse(
        String gymName,
        BigDecimal amount,
        Currency currency
) {
    public RevenueReportResponse {
        if(amount == null) amount = BigDecimal.ZERO;
        if(currency == null) currency = Currency.PLN; // to avoid nulls when there is no revenue
    }
}