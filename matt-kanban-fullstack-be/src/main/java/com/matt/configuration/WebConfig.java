package com.matt.configuration;

import com.matt.interceptors.RequestResponseLoggingInterceptor;
import com.matt.interceptors.TokenValidationInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final RequestResponseLoggingInterceptor requestResponseLoggingInterceptor;
    private final TokenValidationInterceptor tokenValidationInterceptor;

    public WebConfig(
            RequestResponseLoggingInterceptor requestResponseLoggingInterceptor,
            TokenValidationInterceptor tokenValidationInterceptor) {
        this.requestResponseLoggingInterceptor = requestResponseLoggingInterceptor;
        this.tokenValidationInterceptor = tokenValidationInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requestResponseLoggingInterceptor)
                .addPathPatterns("/**");

        registry.addInterceptor(tokenValidationInterceptor)
                .addPathPatterns("/**");
    }
}