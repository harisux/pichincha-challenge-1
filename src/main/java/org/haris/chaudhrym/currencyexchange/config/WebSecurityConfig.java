package org.haris.chaudhrym.currencyexchange.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Bean
    public SecurityWebFilterChain handleSecurityWebFilterChain(ServerHttpSecurity http) {
        // Excluding swagger endpoints
        // Swagger UI is accesible at -> http://localhost:8085/swagger-ui/index.html
        return http.authorizeExchange(exchanges -> exchanges
                .pathMatchers(
                    "/api/v1/exchange",
                    "/v3/api-docs/**",    
                    "/swagger-ui/**",  
                    "/swagger-ui.html"
                ).permitAll()
        )
        .csrf(csrf -> csrf.disable())
        .build();
    }

}
