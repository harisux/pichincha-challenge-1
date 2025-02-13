package org.haris.chaudhrym.currencyexchange.service;

import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;

import reactor.core.publisher.Mono;

public interface ConvertAmountService {
    
    Mono<ConvertAmountResponse> convertExchangeAmount(Mono<ConvertAmountRequest> request);
    
}
