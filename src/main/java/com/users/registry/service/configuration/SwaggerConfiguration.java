package com.users.registry.service.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("user-registry-service-api")
                .packagesToScan("com.users.registry")
                .pathsToMatch("/**")
                .build();
    }
}
