package org.haris.chaudhrym.currencyexchange.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
            .defaultHeaders(headers -> {
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(List.of(MediaType.APPLICATION_JSON));
            })
            .build();
    }
    
}
