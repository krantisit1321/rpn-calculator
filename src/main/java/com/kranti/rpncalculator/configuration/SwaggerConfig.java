package com.kranti.rpncalculator.configuration;

import java.io.IOException;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.xmlpull.v1.XmlPullParserException;

import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket api() throws IOException, XmlPullParserException {
        return new Docket(DocumentationType.SWAGGER_2).select()
                .apis(RequestHandlerSelectors.basePackage("com.kranti.rpncalculator.controller"))
                .paths(PathSelectors.any()).build().apiInfo(metaData()).useDefaultResponseMessages(false);
    }

    private ApiInfo metaData() {
        ApiInfo apiInfo = new ApiInfoBuilder().title("RPN Calculator API Services")
                .description("REST API Service For RPN Calculator").version("1.0")
                .contact(new Contact("Kranti", "Kranti", "krantisit@gmail.com"))
                .termsOfServiceUrl("https://terms.service").build();
        return apiInfo;
    }
}
