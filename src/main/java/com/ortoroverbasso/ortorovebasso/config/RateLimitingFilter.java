package com.ortoroverbasso.ortorovebasso.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/*")
public class RateLimitingFilter implements Filter {

    // Esempio di variabili per il rate limit
    private static final int MAX_REQUESTS = 100; // Max 100 richieste
    private static final long TIME_FRAME = 60 * 1000; // Periodo di tempo di 1 minuto

    private static long lastRequestTime = System.currentTimeMillis();
    private static int requestCount = 0;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // Inizializzazione del filtro (se necessario)
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        long currentTime = System.currentTimeMillis();

        // Resetta il contatore dopo il periodo di tempo definito
        if (currentTime - lastRequestTime > TIME_FRAME) {
            requestCount = 0;
            lastRequestTime = currentTime;
        }

        // Aumenta il contatore delle richieste
        requestCount++;

        if (requestCount > MAX_REQUESTS) {
            response.getWriter().write("Rate limit exceeded");
            return;
        }

        // Se il rate limit non è stato superato, passa al filtro successivo
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup (se necessario)
    }
}
