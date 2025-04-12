package com.matt.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * How to access the SpringDoc.
 *
 * UI: swagger-ui/index.htm
 * e.g. http://localhost:8090/api/swagger-ui/index.htm
 *
 * JSON: v3/api-docs
 * e.g. http://localhost:8090/api/v3/api-docs
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI customOpenApi() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("openApiSecurityScheme", new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .bearerFormat("JWT")
                                .in(SecurityScheme.In.HEADER)
                                .name("Authorization")
                                .scheme("Bearer")))
                .info(new Info()
                        .title("Comments Tree")
                        .version("0.0.1-SNAPSHOT")
                        .description("")
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")));
    }
}