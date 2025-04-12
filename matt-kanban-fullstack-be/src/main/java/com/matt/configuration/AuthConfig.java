package com.matt.configuration;

import com.matt.configuration.model.ExcludePath;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

@Data
@Component
@ConfigurationProperties(prefix = "auth")
public class AuthConfig {
    private List<ExcludePath> excludePaths;
}