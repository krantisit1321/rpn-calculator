package com.kranti.rpncalculator.exception;

import java.util.EmptyStackException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class CalculationExceptionHandler {

    @Autowired
    private ExceptionResponse response;

    @ExceptionHandler({ NullPointerException.class })
    public ResponseEntity<ExceptionResponse> handleNullPointerException(NullPointerException ex) {
        this.response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        this.response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({ CalculationException.class, ArithmeticException.class })
    public ResponseEntity<ExceptionResponse> handleInvalidInputExpression(Exception ex) {
        this.response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        this.response.setErrorMessage(ex.getMessage());
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ NumberFormatException.class, EmptyStackException.class })
    public ResponseEntity<ExceptionResponse> handleNumberFormatException(Exception ex) {
        this.response.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
        this.response.setErrorMessage("Invalid Expression.");
        return new ResponseEntity<ExceptionResponse>(response, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
