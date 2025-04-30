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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS)) // Stateless per le API
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/").permitAll() // Rendi disponibile la home
            .requestMatchers("/api/**").permitAll() // Consenti accesso alle API senza login
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger
            .requestMatchers("/admin").hasRole("ADMIN") // Solo per admin
            .requestMatchers("/dashboard").hasRole("ADMIN") // Solo per admin
            .requestMatchers("/cart").authenticated() // Proteggi la rotta del carrello
            .requestMatchers("/cart/merge").authenticated() // Merge carrelli per utenti autenticati
        )
        .formLogin(login -> login.disable()) // Disabilita il login tradizionale
        .logout(logout -> logout.disable()) // Disabilita il logout tradizionale
        .addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class); // Aggiungi il filtro di
                                                                                              // autenticazione

    return http.build();
  }

  // Crea un filtro per la gestione dell'autenticazione con token o sessione
  @Bean
  public AuthenticationFilter authenticationFilter() {
    return new AuthenticationFilter();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("http://localhost:3000"); // Consenti solo localhost:3000
    corsConfiguration.addAllowedMethod("*"); // Permetti tutti i metodi HTTP
    corsConfiguration.addAllowedHeader("*"); // Permetti tutti gli header
    corsConfiguration.setAllowCredentials(true); // Consenti credenziali (cookie, auth headers, etc.)

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
