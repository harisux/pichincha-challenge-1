package org.haris.chaudhrym.currencyexchange.service.impl;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.persistence.ExchangeRateRepository;
import org.haris.chaudhrym.currencyexchange.persistence.UserExchangeLogRepository;
import org.haris.chaudhrym.currencyexchange.persistence.entity.UserExchangeLog;
import org.haris.chaudhrym.currencyexchange.service.ConvertAmountService;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class ConvertAmountServiceImpl implements ConvertAmountService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final UserExchangeLogRepository userLogRepository;

    private final static String RATE_SEPARATOR = "/";

    @Override
    public Mono<ConvertAmountResponse> convertExchangeAmount(Mono<ConvertAmountRequest> request) {
        return request.flatMap(req -> 
                exchangeRateRepository.findByOriginAndDestination(req.getOrigin(), req.getDestination())
                    .switchIfEmpty(Mono.error(new RuntimeException("Exchange rate not found!")))
                    .map(rate -> ConvertAmountState.builder().amount(req.getAmount()).exchangeRate(rate).build())
            )
            .map(state -> { 
                ConvertAmountResponse res = performConversion(state);
                state.setConvertedResponse(res);
                return state;
            })
            .flatMap(state -> { 
                UserExchangeLog userLog = createLog(state);
                return userLogRepository.save(userLog)
                    .map(saved -> { 
                        state.setUserLog(saved);
                        return state;
                    });
            })
            .map(state -> state.getConvertedResponse())
        ;
    }

    private ConvertAmountResponse performConversion(ConvertAmountState state) {
        ConvertAmountResponse res = new ConvertAmountResponse();
        res.setOriginDestination(state.getExchangeRate().getOrigin() + RATE_SEPARATOR + state.getExchangeRate().getDestination());
        res.setRate(state.getExchangeRate().getRate().doubleValue());
        res.setConvertedAmount(state.getAmount() * state.getExchangeRate().getRate().doubleValue());
        return res;
    }

    private UserExchangeLog createLog(ConvertAmountState state) {
        UserExchangeLog userLog = new UserExchangeLog();
        userLog.setUserId(Long.valueOf(12345));
        userLog.setUserName("testUser");
        userLog.setOrigin_amount(state.getAmount());
        userLog.setDestination_amount(state.getConvertedResponse().getConvertedAmount());
        userLog.setRate(state.getExchangeRate().getRate().doubleValue());
        userLog.setTransactionDate(LocalDateTime.now());
        return userLog;
    }

    
}
