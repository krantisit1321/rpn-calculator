package com.kranti.rpncalculator.exception;

public class CalculationException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private String message;

    public CalculationException(String message) {
        super(message);
        this.message = message;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
