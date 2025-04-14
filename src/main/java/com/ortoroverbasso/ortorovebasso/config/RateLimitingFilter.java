package com.ortoroverbasso.ortorovebasso.config;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;

@WebFilter("/admin")
public class RateLimitingFilter implements Filter {

    private static final int MAX_REQUESTS = 100;
    private static final long TIME_FRAME = 60 * 1000;

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

        if (currentTime - lastRequestTime > TIME_FRAME) {
            requestCount = 0;
            lastRequestTime = currentTime;
        }

        requestCount++;

        if (requestCount > MAX_REQUESTS) {
            response.getWriter().write("Rate limit exceeded");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        // Cleanup (se necessario)
    }
}
