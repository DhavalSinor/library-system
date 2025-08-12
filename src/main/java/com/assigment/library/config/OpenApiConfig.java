package com.assigment.library.config;


import io.swagger.v3.oas.models.info.*;
import io.swagger.v3.oas.models.*;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {
    @Bean
    public OpenAPI libraryOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Library API").version("1.0").description("Simple Library REST API"));
    }
}

