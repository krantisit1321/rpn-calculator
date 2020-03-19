package com.kranti.rpncalculator.service;

import com.kranti.rpncalculator.model.CalculationResponse;

public interface CalculationService {

    CalculationResponse calculate(String expression) throws Exception;

}
