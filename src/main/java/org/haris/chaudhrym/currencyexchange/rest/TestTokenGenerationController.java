package org.haris.chaudhrym.currencyexchange.rest;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import reactor.core.publisher.Mono;

//Controller for token generation, only for dev / testing purposes

@Profile("dev")
@RestController
@RequestMapping("/testing/token")
public class TestTokenGenerationController {
 
    @Value("${jwt-auth.secret}")
    private String jwtSecret;

    @GetMapping
    public ResponseEntity<Mono<String>> generateTestToken() {
        String jwt = Jwts.builder()
                .subject("testsubject")
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(Date.from(Instant.now().plus(1, ChronoUnit.HOURS)))
                .signWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .compact(); 
        return ResponseEntity.status(HttpStatus.OK).body(Mono.just(jwt));
    }
    
}
