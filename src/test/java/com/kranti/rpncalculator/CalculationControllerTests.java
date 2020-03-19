package com.kranti.rpncalculator;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Description;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RPNCalcApplication.class)
@AutoConfigureMockMvc
@ActiveProfiles("local")
public class CalculationControllerTests extends TestBase {

    private static final String RPN_CALC_BASEURL = "/calc/v1/results";
    private static final String QUERY_PARAM = "?expression=";
    private static final String RPN_CALC_SHORTEXPRESSION = RPN_CALC_BASEURL + QUERY_PARAM;

    private static final String RPN_CALC_TEST_1 = RPN_CALC_SHORTEXPRESSION + "3  4 *";
    private static final String RPN_CALC_TEST_2 = RPN_CALC_SHORTEXPRESSION + "3  4 * 5 +";
    private static final String RPN_CALC_TEST_3 = RPN_CALC_SHORTEXPRESSION + "3.5  4.6 * 5 +";
    private static final String RPN_CALC_TEST_4 = RPN_CALC_SHORTEXPRESSION + "    3    4 *     5 +      ";

    private static final String RPN_CALC_TEST_5 = RPN_CALC_SHORTEXPRESSION + "3  4";
    private static final String RPN_CALC_TEST_6 = RPN_CALC_SHORTEXPRESSION + "  ";
    private static final String RPN_CALC_TEST_7 = RPN_CALC_SHORTEXPRESSION + "3 4 * 56+";
    private static final String RPN_CALC_TEST_8 = RPN_CALC_SHORTEXPRESSION + "3 4 * 5 %";

    private static final String RPN_CALC_CONTENT_1 = "{\"expression\": \"3  4 *\"}";
    private static final String RPN_CALC_CONTENT_2 = "{\"expression\": \"3  4 * 5 +\"}";
    private static final String RPN_CALC_CONTENT_3 = "{\"expression\": \"3.5  4.6 * 5 +\"}";
    private static final String RPN_CALC_CONTENT_4 = "{\"expression\": \"    3    4 *     5 +      \"}";

    @Test
    @Description("Test to verify Calculation result for a given expression")
    public void getResultTest() throws Exception {
        this.apiTests(new String[] { RPN_CALC_TEST_1, RPN_CALC_TEST_2, RPN_CALC_TEST_3, RPN_CALC_TEST_4 });
    }

    @Test
    @Description("Test to verify Calculation result for a given invalid expression")
    public void getResultNegativeTest() throws Exception {
        this.apiValueTest(RPN_CALC_TEST_5, "errorMessage", "Invalid expression.", "4xx");
        this.apiValueTest(RPN_CALC_TEST_6, "errorMessage", "RPN Expression is Empty or null.", "4xx");
        this.apiValueTest(RPN_CALC_TEST_7, "errorMessage", "Invalid expression.", "4xx");
        this.apiValueTest(RPN_CALC_TEST_8, "errorMessage",
                "Sufficient operators may not provided or unsupported operators provided. Supported Operators are :+-*/",
                "4xx");
    }

    @Test
    @Description("Test to verify Calculation result for a lengthy expression")
    public void getResultForLengthyExpressions() throws Exception {
        Map<String, String> calculationContentMap = new HashMap<>();
        calculationContentMap.put(RPN_CALC_BASEURL, RPN_CALC_CONTENT_1);
        calculationContentMap.put(RPN_CALC_BASEURL, RPN_CALC_CONTENT_2);
        calculationContentMap.put(RPN_CALC_BASEURL, RPN_CALC_CONTENT_3);
        calculationContentMap.put(RPN_CALC_BASEURL, RPN_CALC_CONTENT_4);
        this.postApiTests(calculationContentMap);
    }
}
