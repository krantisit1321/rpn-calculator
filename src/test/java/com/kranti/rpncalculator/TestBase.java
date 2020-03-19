package com.kranti.rpncalculator;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;
import java.util.Map;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

public class TestBase {

    private static final String CLIENT_ERROR = "4xx";
    private static final String SERVER_ERROR = "5xx";

    protected MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

    protected MockMvc mockMvc = null;

    @Autowired
    protected WebApplicationContext webApplicationContext;

    @Before
    public void setup() throws Exception {
        if (mockMvc == null) {
            mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        }
    }

    public void apiTest(String url) throws Exception {
        if (url != null && url.length() > 0) {
            System.out.println("\n========== Test Url : '" + url + "' ==========\n");
            mockMvc.perform(get(url)).andExpect(status().isOk()).andExpect(content().contentType(contentType));
        }
    }

    public void apiTests(String[] urls) throws Exception {
        if (urls != null && urls.length > 0) {
            for (String url : urls) {
                apiTest(url);
            }
        }
    }

    public void apiValueTest(String url, String key, Object value, String errorType) throws Exception {
        if (errorType.equalsIgnoreCase(CLIENT_ERROR)) {
            mockMvc.perform(get(url)).andExpect(status().is4xxClientError())
                    .andExpect(content().contentType(contentType)).andExpect(jsonPath("$." + key, is(value)));
        } else if (errorType.equalsIgnoreCase(SERVER_ERROR)) {
            mockMvc.perform(get(url)).andExpect(status().is5xxServerError())
                    .andExpect(content().contentType(contentType)).andExpect(jsonPath("$." + key, is(value)));
        }
    }

    public void postApiTest(String url, String content) throws Exception {
        if (url != null && url.length() > 0) {
            System.out.println("\n========== Test Url : '" + url + "' ==========\n");
            mockMvc.perform(post(url).contentType(contentType).content(content)).andExpect(status().isOk())
                    .andExpect(content().contentType(contentType));
        }
    }

    public void postApiTests(Map<String, String> urls) throws Exception {
        if (urls != null) {
            for (Map.Entry<String, String> entry : urls.entrySet()) {
                postApiTest(entry.getKey(), entry.getValue());
            }
        }
    }

}
