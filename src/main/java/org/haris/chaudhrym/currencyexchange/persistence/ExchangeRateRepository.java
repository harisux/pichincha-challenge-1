package org.haris.chaudhrym.currencyexchange.persistence;

import org.haris.chaudhrym.currencyexchange.persistence.entity.ExchangeRate;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import reactor.core.publisher.Mono;

public interface ExchangeRateRepository extends ReactiveCrudRepository<ExchangeRate, Long>{
    
    Mono<ExchangeRate> findByOriginAndDestination(String origin, String destination);

}
