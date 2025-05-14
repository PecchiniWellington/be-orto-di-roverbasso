package com.ortoroverbasso.ortorovebasso.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import com.ortoroverbasso.ortorovebasso.security.JwtAuthenticationEntryPoint;
import com.ortoroverbasso.ortorovebasso.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

  private final JwtAuthenticationEntryPoint authenticationEntryPoint;
  private final JwtAuthenticationFilter authenticationFilter;

  // Rotte pubbliche
  private static final String[] WHITE_LIST_URLS = {
      "/api/auth/login",
      "/api/auth/register",
      "/v2/api-docs",
      "/v3/api-docs",
      "/v3/api-docs/**",
      "/swagger-resources",
      "/swagger-resources/**",
      "/swagger-ui/**",
      "/swagger-ui.html",
      "/api/cart/guest/**",
      "/api/cart/create",
      "/api/cart/*",
      "/api/categories/**",
      "/api/products/**",
      "/api/auth/check"
  };

  public SecurityConfig(
      JwtAuthenticationEntryPoint authenticationEntryPoint,
      JwtAuthenticationFilter authenticationFilter) {
    this.authenticationEntryPoint = authenticationEntryPoint;
    this.authenticationFilter = authenticationFilter;
  }

  @Bean
  public static PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(List.of("http://localhost:3000"));
    configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));
    configuration.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Requested-With", "Accept"));
    configuration.setAllowCredentials(true); // fondamentale per usare i cookie
    configuration.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration);
    return source;
  }

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .csrf(AbstractHttpConfigurer::disable)
        .cors(cors -> cors.configurationSource(corsConfigurationSource()))
        .exceptionHandling(exception -> exception.authenticationEntryPoint(authenticationEntryPoint))
        .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(WHITE_LIST_URLS).permitAll()

            // Info personale
            .requestMatchers(HttpMethod.GET, "/api/users/me").authenticated()

            // Preferenze, profilo, indirizzi (utente autenticato)
            .requestMatchers("/api/users/*/preferences/**").authenticated()
            .requestMatchers("/api/users/*/profile/**").authenticated()
            .requestMatchers("/api/users/*/addresses/**").authenticated()

            // Gestione utenti (admin e contributor)
            .requestMatchers(HttpMethod.GET, "/api/users/all").hasRole("ADMIN")
            .requestMatchers(HttpMethod.GET, "/api/users/**").hasAnyRole("USER", "ADMIN", "CONTRIBUTOR")
            .requestMatchers(HttpMethod.POST, "/api/users/**").hasRole("ADMIN")
            .requestMatchers(HttpMethod.PUT, "/api/users/**").hasAnyRole("ADMIN", "USER")
            .requestMatchers(HttpMethod.DELETE, "/api/users/**").hasRole("ADMIN")

            // Tutto il resto richiede autenticazione
            .anyRequest().authenticated());

    http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

    return http.build();
  }
}
