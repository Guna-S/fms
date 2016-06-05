package com.fms.core.config;

import com.google.common.base.Predicate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.List;

import static springfox.documentation.builders.PathSelectors.regex;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    private static final List<Predicate<String>> PATHS = Arrays.asList(
        regex(".*fms*"));


    @Bean
    public Docket fmsAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(new ApiInfoBuilder()
                .title("File Management System API")
                .description("Provides APIs for the file uploading and downloading.")
                .version("0.1.0")
                .build())
            .select()
            .paths(PathSelectors.any())
            .build();
    }
}