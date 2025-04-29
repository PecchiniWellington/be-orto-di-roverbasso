package com.ortoroverbasso.ortorovebasso.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

@Configuration // Indica che questa è una classe di configurazione
public class CorsConfig {

    @Bean
    public FilterRegistrationBean<CorsFilter> corsFilter() {
        // Crea la configurazione CORS
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration corsConfig = new CorsConfiguration();
        corsConfig.addAllowedOrigin("http://localhost:3000"); // Permetti richieste da localhost:3000
        corsConfig.addAllowedMethod("GET"); // Permetti GET
        corsConfig.addAllowedMethod("POST"); // Permetti POST
        corsConfig.addAllowedMethod("OPTIONS"); // Permetti OPTIONS (per la richiesta preflight)
        corsConfig.addAllowedHeader("*"); // Permetti tutte le intestazioni
        corsConfig.setAllowCredentials(true); // Permetti le credenziali (cookie, auth headers, etc.)

        // Registra la configurazione CORS per tutte le richieste
        source.registerCorsConfiguration("/**", corsConfig);

        // Registra il filtro CORS come primo filtro da applicare
        FilterRegistrationBean<CorsFilter> registrationBean = new FilterRegistrationBean<>(new CorsFilter(source));
        registrationBean.setOrder(0); // Assicurati che il filtro CORS venga applicato per primo
        return registrationBean;
    }
}
