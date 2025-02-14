package org.haris.chaudhrym.currencyexchange.config;

import java.util.Collections;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
public class JwtAuthFilter implements WebFilter {

    private final String jwtSecret;

    private final List<String> publicEndpoints;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        //Skip check for public endpoints
        AntPathMatcher matcher = new AntPathMatcher();
        String path = exchange.getRequest().getPath().toString();
        for (String publicUrl : publicEndpoints) {
            if (matcher.match(publicUrl, path)) return chain.filter(exchange);
        }

        //Extract jwt from header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || authHeader.isEmpty() 
                || !authHeader.startsWith("Bearer ")) { 
            return invalidateRequest(exchange);
        }

        //Check jwt validity
        String jwt = authHeader.split("Bearer")[1].trim();
        try {
            Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(jwt);
        } catch (Exception exception) {
            return invalidateRequest(exchange);
        }

        return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(
            new UsernamePasswordAuthenticationToken("authUser", null, Collections.emptyList())
        ));
    }

    private Mono<Void> invalidateRequest(ServerWebExchange exchange) {
        exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
        return exchange.getResponse().setComplete();
    }
    
}
