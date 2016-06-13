package com.fms.core.config;

import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableJpaRepositories(basePackages = "com.fms.core.repository")
@EnableTransactionManagement
@EntityScan(basePackages = "com.fms.core.model")
@EnableSwagger2
public class FmsConfiguration {

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
