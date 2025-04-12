package com.matt.interceptors;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Slf4j
@Component
public class RequestResponseLoggingInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        MDC.put("requestId", String.valueOf(System.nanoTime()));
        logRequest(request);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        try {
            logResponse(response);
        } finally {
            MDC.clear();
        }
    }

    private void logRequest(HttpServletRequest request) {
        log.info("Request received: {} {} from IP: {}", request.getMethod(), request.getRequestURI(), request.getRemoteAddr());
    }

    private void logResponse(HttpServletResponse response) {
        log.info("Response sent with status: {}", response.getStatus());
    }
}
