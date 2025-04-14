package com.ortoroverbasso.ortorovebasso.config;

import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.server.ErrorPageRegistrar;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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
        /*
         * .sessionManagement(management -> management
         * .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
         */
        /*
         * .headers(headers -> headers
         * .httpStrictTransportSecurity(hsts -> hsts
         * .includeSubDomains(true)
         * .maxAgeInSeconds(31536000)))
         */
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/").permitAll() // Permette accesso alla home
            .requestMatchers("/api/**").permitAll()
            .requestMatchers(HttpMethod.POST, "/api/productinformationbysku/**").permitAll() // Explicitly allow POST
            .requestMatchers("/admin").hasRole("ADMIN") // Admin protetto
            .requestMatchers("/dashboard").hasRole("ADMIN") // Dashboard protetto
            .requestMatchers("/profile").authenticated() // Profilo richiede autenticazione
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll() // Swagger pubblico
            .anyRequest().authenticated()) // Altre richieste richiedono autenticazione
        .formLogin(login -> login
            .loginPage("/login") // Configura la pagina di login
            .permitAll()) // Permette accesso alla pagina di login senza autenticazione
        .logout(logout -> logout
            .logoutUrl("/logout") // Configura il logout
            .permitAll()) // Permette il logout senza autenticazione
        .exceptionHandling(handling -> handling
            .accessDeniedPage("/access-denied")); // Configura la pagina di accesso negato
    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("http://localhost:3000");
    corsConfiguration.addAllowedMethod("*"); // Permetti tutti i metodi
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
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
