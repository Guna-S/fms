package com.fms.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.DeferredResultMethodReturnValueHandler;

import java.util.List;

@Configuration
@EnableWebMvc
@ComponentScan("com.fms.core")
public class FmsConfiguration extends WebMvcConfigurerAdapter {


    @Override
    public void addReturnValueHandlers(final List<HandlerMethodReturnValueHandler> returnValueHandlers) {
        returnValueHandlers.add(new DeferredResultMethodReturnValueHandler());
    }
}
