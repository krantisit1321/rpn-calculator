package com.kranti.rpncalculator.model;

import java.math.BigDecimal;

import org.springframework.stereotype.Component;

@Component
public class CalculationResponse {

    private BigDecimal result;

    public BigDecimal getResult() {
        return result;
    }

    public void setResult(BigDecimal result) {
        this.result = result;
    }

}
