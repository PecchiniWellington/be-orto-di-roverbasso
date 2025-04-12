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
        .sessionManagement(session -> session
            .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED) // Crea sessione solo quando necessario
            .invalidSessionUrl("/login") // URL di reindirizzamento se la sessione scade
        )
        .headers(headers -> headers
            .httpStrictTransportSecurity(hsts -> hsts
                .includeSubDomains(true)
                .maxAgeInSeconds(31536000))) // Abilita HSTS per proteggere l'uso di HTTPS
        .authorizeHttpRequests(requests -> requests
            .requestMatchers("/").permitAll()
            .requestMatchers("/admin").hasRole("ADMIN")
            .requestMatchers("/dashboard").hasRole("ADMIN")
            .requestMatchers("/profile").authenticated()
            .requestMatchers("/swagger-ui/**", "/v3/api-docs/**").permitAll()
            .anyRequest().authenticated())
        .formLogin(login -> login
            .loginPage("/login")
            .permitAll())
        .logout(logout -> logout
            .logoutUrl("/logout")
            .permitAll())
        .exceptionHandling(handling -> handling
            .accessDeniedPage("/access-denied"))
        .addFilterBefore(new RateLimitingFilter(), UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration corsConfiguration = new CorsConfiguration();
    corsConfiguration.addAllowedOrigin("http://localhost:3000");
    corsConfiguration.addAllowedMethod("*");
    corsConfiguration.addAllowedHeader("*");
    corsConfiguration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", corsConfiguration);
    corsConfiguration.addAllowedMethod("GET");
    corsConfiguration.addAllowedMethod("POST");
    corsConfiguration.addAllowedMethod("PUT");
    corsConfiguration.addAllowedMethod("DELETE");
    corsConfiguration.addAllowedMethod("PATCH");
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
