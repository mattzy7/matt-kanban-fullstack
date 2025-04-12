package com.matt.interceptors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.benmanes.caffeine.cache.Cache;
import com.matt.cache.ThreadLocalCache;
import com.matt.configuration.AuthConfig;
import com.matt.configuration.JwtConfiguration;
import com.matt.configuration.model.ExcludePath;
import com.matt.dto.response.APIResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.util.PatternMatchUtils;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

import static com.matt.utils.Constants.CACHE_KEY_TOKEN_PREFIX;


@Slf4j
@Component
public class TokenValidationInterceptor implements HandlerInterceptor {

    @Autowired
    private AuthConfig authConfig;
    @Autowired
    private JwtConfiguration jwtConfiguration;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private Cache<String, String> caffeineCache;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info(request.getRequestURI());

        // Check if path is to be excluded from authentication validation
        for (ExcludePath excludePath : authConfig.getExcludePaths()) {
            boolean isExcludePath = PatternMatchUtils.simpleMatch(excludePath.getUrl(), request.getRequestURI());
            boolean isExcludeHttpMethod = Arrays.stream(excludePath.getMethod().split(","))
                    .map(String::trim)
                    .anyMatch(method ->
                            method.equalsIgnoreCase("*") || method.equalsIgnoreCase(request.getMethod()));

            if (isExcludePath && isExcludeHttpMethod) {
                return true;
            }
        }

        // Check if Authorization header is missing or not in the correct format
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || !jwtToken.startsWith("Bearer ")) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            log.warn("Authorization token is missing or malformed {}", jwtToken);
            return writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Authorization token is missing or malformed");
        }

        // Check if the token has expired
        jwtToken = jwtToken.substring(7);
        String token = caffeineCache.getIfPresent(CACHE_KEY_TOKEN_PREFIX + jwtToken);
        if(Strings.isEmpty(token)) {
            log.warn("Token is not expired: {}", token);
            return writeErrorResponse(response, HttpServletResponse.SC_UNAUTHORIZED, "Token is expired: " + jwtToken);
        }

        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        ThreadLocalCache.clear();
    }

    private boolean writeErrorResponse(HttpServletResponse response, int statusCode, String message) {
        try {
            response.setStatus(statusCode);
            response.setContentType(MediaType.APPLICATION_JSON_VALUE);

            APIResponse<Void> apiResponse = APIResponse.failure(message);
            String jsonResponse = objectMapper.writeValueAsString(apiResponse);

            response.getWriter().write(jsonResponse);
            response.getWriter().flush();
        } catch (Exception e) {
            log.error("Failed to write error response: {}", e.getMessage(), e);
        }
        return false;
    }
}