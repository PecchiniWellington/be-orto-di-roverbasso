package com.ortoroverbasso.ortorovebasso.security;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.ortoroverbasso.ortorovebasso.config.EnvironmentConfig;
import com.ortoroverbasso.ortorovebasso.service.cart.ICartService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(JwtAuthenticationFilter.class);

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private EnvironmentConfig environmentConfig;

    @Autowired
    private ICartService cartService;

    private final List<String> publicPaths = List.of(
            "/api/email/send",
            "/api/auth/login",
            "/api/auth/register",
            "/api/auth/check",
            "/swagger",
            "/v3/api-docs");

    private final List<String> skipCartTokenPaths = List.of(
            "/api/products",
            "/api/categories",
            "/api/auth");

    private final Object cartLock = new Object();

    @Override
    protected void doFilterInternal(HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        String path = request.getRequestURI();

        // ⛔ Escludi richieste pubbliche dalla logica di autenticazione
        if (publicPaths.stream().anyMatch(path::startsWith)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            // ==== Autenticazione JWT ====
            String token = getJwtFromRequest(request);

            if (StringUtils.hasText(token) && tokenProvider.validateToken(token)) {
                String userEmail = tokenProvider.getUserEmailFromToken(token);

                UserDetails userDetails = userDetailsService.loadUserByUsername(userEmail);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }

            // ==== Salta gestione cartToken se non necessario ====
            if (skipCartTokenPaths.stream().anyMatch(path::startsWith)) {
                filterChain.doFilter(request, response);
                return;
            }

            // ==== Gestione cartToken ====
            boolean isAuthenticatedUser = SecurityContextHolder.getContext().getAuthentication() != null
                    && SecurityContextHolder.getContext().getAuthentication().isAuthenticated()
                    && !(SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof String);

            String cartToken = null;

            // Recupera cartToken dal cookie
            Cookie[] cookies = request.getCookies();
            if (cookies != null) {
                for (Cookie cookie : cookies) {
                    if ("cartToken".equals(cookie.getName())) {
                        cartToken = cookie.getValue();
                        break;
                    }
                }
            }

            // Utente autenticato → non generare cartToken
            if (isAuthenticatedUser && (cartToken == null || cartToken.isBlank())) {
                logger.info("[JWT FILTER] Utente autenticato senza cartToken: nessun nuovo token generato.");
                filterChain.doFilter(request, response);
                return;
            }

            // Guest → crea cartToken se mancante
            if (!isAuthenticatedUser && (cartToken == null || cartToken.isBlank())) {
                cartToken = UUID.randomUUID().toString();
                Cookie newCartCookie = new Cookie("cartToken", cartToken);
                newCartCookie.setHttpOnly(true);
                newCartCookie.setSecure(environmentConfig.isProduction());
                newCartCookie.setPath("/");
                newCartCookie.setMaxAge(60 * 60 * 24 * 7); // 7 giorni
                response.addCookie(newCartCookie);
                logger.info("[JWT FILTER] Nuovo cartToken creato per guest: {}", cartToken);
            }

            if (cartToken != null) {
                request.setAttribute("cartToken", cartToken);

                synchronized (cartLock) {
                    if (!cartService.existsByCartToken(cartToken)) {
                        cartService.createCartWithToken(cartToken);
                        logger.info("[JWT FILTER] Cart creato per token: {}", cartToken);
                    } else {
                        logger.info("[JWT FILTER] Cart già esistente per token: {}", cartToken);
                    }
                }
            }

        } catch (Exception ex) {
            logger.error("[JWT FILTER] Errore durante l'elaborazione", ex);
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("JWT".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }

        return null;
    }
}
