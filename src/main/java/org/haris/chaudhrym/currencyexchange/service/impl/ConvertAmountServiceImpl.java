package org.haris.chaudhrym.currencyexchange.service.impl;

import java.math.BigDecimal;

import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.persistence.ExchangeRateRepository;
import org.haris.chaudhrym.currencyexchange.persistence.entity.ExchangeRate;
import org.haris.chaudhrym.currencyexchange.service.ConvertAmountService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConvertAmountServiceImpl implements ConvertAmountService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final static String RATE_SEPARATOR = "/";

    @Override
    public Mono<ConvertAmountResponse> convertExchangeAmount(Mono<ConvertAmountRequest> request) {
        return request.flatMap(req -> 
                exchangeRateRepository.findByOriginAndDestination(req.getOrigin(), req.getDestination())
                .switchIfEmpty(Mono.error(new RuntimeException("Rate not found!")))
                .map(rate -> new ExchangeRateWithAmount(rate, req.getAmount()))
            )
            .map(rateAmount -> performConversion(rateAmount.getAmount(), rateAmount.getExchangeRate()))
        ;
            
    }

    private ConvertAmountResponse performConversion(Double amount, ExchangeRate exchangeRate) {
        ConvertAmountResponse res = new ConvertAmountResponse();
        res.setOriginDestination(exchangeRate.getOrigin() + RATE_SEPARATOR + exchangeRate.getDestination());
        res.setRate(exchangeRate.getRate().doubleValue());
        res.setConvertedAmount(amount * exchangeRate.getRate().doubleValue());
        return res;
    }

    
}
