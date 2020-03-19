package com.kranti.rpncalculator.service;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.kranti.rpncalculator.model.CalculationResponse;
import com.kranti.rpncalculator.utils.CalculationUtility;

@Service
public class CalculationServiceImpl implements CalculationService {

    @Autowired
    private CalculationResponse calResponse;

    @Override
    public CalculationResponse calculate(String expression) throws Exception {
        expression = CalculationUtility.sanitizeExpression(expression);
        if (CalculationUtility.isValidExpression(expression)) {
            BigDecimal calResult = CalculationUtility.calculate(expression);
            this.calResponse.setResult(calResult);
            return this.calResponse;
        }
        return null;
    }

}