package com.ortoroverbasso.ortorovebasso.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentConfig {

    @Value("${app.environment:local}")
    private String environment;

    public boolean isProduction() {
        return !"local".equalsIgnoreCase(environment) && !"dev".equalsIgnoreCase(environment);
    }

    public String getEnvironment() {
        return environment;
    }
}
