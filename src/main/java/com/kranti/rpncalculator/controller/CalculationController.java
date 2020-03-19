package com.kranti.rpncalculator.controller;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kranti.rpncalculator.model.CalculationResponse;
import com.kranti.rpncalculator.model.ExpressionRequest;
import com.kranti.rpncalculator.service.CalculationService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import springfox.documentation.annotations.ApiIgnore;

@Api(tags = "Calculation Services")
@RestController
@RequestMapping("/calc")
public class CalculationController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CalculationController.class);

    @Autowired
    private CalculationService calService;

    @ApiIgnore
    @RequestMapping(value = "/v1/ping", method = RequestMethod.GET)
    public @ResponseBody HttpEntity<?> index() throws IOException {
        return new ResponseEntity<String>("Welcome to the Calculation Services API", HttpStatus.OK);
    }

    @ApiOperation(value = "Get Result for a given RPN Expression")
    @RequestMapping(value = "/v1/results", method = RequestMethod.GET, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody HttpEntity<CalculationResponse> getResult(
            @RequestParam(value = "expression", defaultValue = "", required = true) String expression)
            throws Exception {
        LOGGER.info("Received Expression : " + expression);
        CalculationResponse calResponse = this.calService.calculate(expression);
        LOGGER.info("Calculation result : " + calResponse.getResult().toString());
        return new ResponseEntity<CalculationResponse>(calResponse, HttpStatus.OK);
    }

    @ApiOperation(value = "Get Result for lengthy RPN Expression")
    @RequestMapping(value = "/v1/results", method = RequestMethod.POST, consumes = {
            MediaType.APPLICATION_JSON_VALUE }, produces = { MediaType.APPLICATION_JSON_VALUE })
    public @ResponseBody HttpEntity<CalculationResponse> getResultForLengthyExpressions(
            @RequestBody ExpressionRequest expressionPayload) throws Exception {
        LOGGER.info("Received Expression : " + expressionPayload.getExpression().toString());
        CalculationResponse calResponse = this.calService.calculate(expressionPayload.getExpression());
        LOGGER.info("Calculation result : " + calResponse.getResult().toString());
        return new ResponseEntity<CalculationResponse>(calResponse, HttpStatus.OK);
    }

}
