package org.haris.chaudhrym.currencyexchange.config;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.SecurityWebFiltersOrder;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.context.NoOpServerSecurityContextRepository;


@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Value("${jwt-auth.secret}")
    private String jwtSecret;

    @Bean
    public SecurityWebFilterChain handleSecurityWebFilterChain(ServerHttpSecurity http) {
        // Excluding swagger endpoints + dummy token endpoint
        // Swagger UI is accesible at -> http://localhost:8085/swagger-ui/index.html
        List<String> publicEndpoints = List.of(
            "/testing/token",
            "/v3/api-docs/**",    
            "/swagger-ui/**",  
            "/swagger-ui.html"
        );

        JwtAuthFilter authFilter = new JwtAuthFilter(jwtSecret, publicEndpoints);
        return http
        .authorizeExchange(exchanges -> exchanges
                .pathMatchers(publicEndpoints.toArray(new String[publicEndpoints.size()])).permitAll()
                .anyExchange().authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .httpBasic(basic -> basic.disable()) //disable basic auth
        .addFilterAt(authFilter, SecurityWebFiltersOrder.AUTHENTICATION) //jwt auth filter
        .securityContextRepository(NoOpServerSecurityContextRepository.getInstance()) //remove sessions
        .build();
    }

}
