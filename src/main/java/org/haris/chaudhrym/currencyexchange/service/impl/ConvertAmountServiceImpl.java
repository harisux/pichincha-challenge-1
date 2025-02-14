package org.haris.chaudhrym.currencyexchange.service.impl;

import java.time.LocalDateTime;

import org.haris.chaudhrym.currencyexchange.exception.ExchangeRateNotFoundException;
import org.haris.chaudhrym.currencyexchange.exception.UserApiException;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.persistence.ExchangeRateRepository;
import org.haris.chaudhrym.currencyexchange.persistence.UserExchangeLogRepository;
import org.haris.chaudhrym.currencyexchange.persistence.entity.UserExchangeLog;
import org.haris.chaudhrym.currencyexchange.service.ConvertAmountService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConvertAmountServiceImpl implements ConvertAmountService {

    private final ExchangeRateRepository exchangeRateRepository;

    private final UserExchangeLogRepository userLogRepository;

    private final WebClient webClient;

    @Value("${external-apis.users-api.base-url}")
    private String usersBaseUrl;

    @Value("${external-apis.users-api.users-path}")
    private String usersPath;

    @Value("${external-apis.users-api.id-key}")
    private String idKey;

    private final static String CURRENCY_SEPARATOR = "/";

    @Override
    public Mono<ConvertAmountResponse> convertExchangeAmount(Mono<ConvertAmountRequest> request) {
        return request
            .flatMap(req -> exchangeRateRepository.findByOriginAndDestination(req.getOrigin(), req.getDestination())
                .switchIfEmpty(Mono.error(new ExchangeRateNotFoundException("Exchange rate for the provided currencies is not available!")))
                .map(rate -> ConvertAmountState.builder()
                        .userId(req.getUserId())
                        .amount(req.getAmount())
                        .exchangeRate(rate)
                        .build()
                )
            )
            .map(state -> { 
                ConvertAmountResponse res = performConversion(state);
                state.setConvertedResponse(res);
                return state;
            })
            .flatMap(state -> fetchUserFromApi(state)
                .map(user -> {
                    state.setUserName(user.getName());
                    return state;
                })
            )
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

    private Mono<UsersApiResponse> fetchUserFromApi(ConvertAmountState state) {
        return webClient.get()
            .uri(usersBaseUrl, uriBuilder -> uriBuilder
                .path(usersPath)
                .queryParam(idKey, state.getUserId())
                .build()
            )
            .retrieve()
            .onStatus(HttpStatusCode::isError, res ->
                Mono.error(new UserApiException("Unable to get users from API!"))
            )
            .bodyToMono(UsersApiResponse[].class)
            .flatMap(users -> { 
                if (users.length == 0) {
                    return Mono.error(new UserApiException("Provided user does not exist!"));
                }
                return Mono.just(users[0]);
            });
    }

    private ConvertAmountResponse performConversion(ConvertAmountState state) {
        ConvertAmountResponse res = new ConvertAmountResponse();
        res.setOriginDestination(state.getExchangeRate().getOrigin() + CURRENCY_SEPARATOR + state.getExchangeRate().getDestination());
        res.setRate(state.getExchangeRate().getRate().doubleValue());
        res.setConvertedAmount(state.getAmount() * state.getExchangeRate().getRate().doubleValue());
        return res;
    }

    private UserExchangeLog createLog(ConvertAmountState state) {
        UserExchangeLog userLog = new UserExchangeLog();
        userLog.setUserId(Long.valueOf(state.getUserId()));
        userLog.setUserName(state.getUserName());
        userLog.setOrigin_amount(state.getAmount());
        userLog.setDestination_amount(state.getConvertedResponse().getConvertedAmount());
        userLog.setRate(state.getExchangeRate().getRate().doubleValue());
        userLog.setTransactionDate(LocalDateTime.now());
        return userLog;
    }
    
}
