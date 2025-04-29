package com.ortoroverbasso.ortorovebasso.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf
            .ignoringRequestMatchers("/api/**"))
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/").permitAll()
            .requestMatchers("/api/**").permitAll() // Assicurati che i percorsi siano corretti per le tue API
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .requestMatchers("/admin").hasRole("ADMIN")
            .requestMatchers("/dashboard").hasRole("ADMIN"))
        .formLogin(login -> login.disable())
        .logout(logout -> logout.disable());
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Consenti solo localhost:3000
    corsConfiguration.addAllowedMethod("*"); // Permetti tutti i metodi HTTP
    corsConfiguration.addAllowedHeader("*"); // Permetti tutti gli header
    corsConfiguration.setAllowCredentials(true); // Consenti credenziali (cookie, auth headers, etc.)

    // Rimuovi setAllowedOrigins(List.of("*")); per evitare conflitti
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration); // Applica la configurazione a tutte le route
    return source;
  }

  @Bean
  public ErrorPageRegistrar errorPageRegistrar() {
    return (registry) -> {
      registry.addErrorPages(
          new ErrorPage(HttpStatus.FORBIDDEN, "/403"),
          new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
    };
  }

}
