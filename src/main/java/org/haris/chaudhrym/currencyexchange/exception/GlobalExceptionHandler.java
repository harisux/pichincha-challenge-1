package org.haris.chaudhrym.currencyexchange.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import reactor.core.publisher.Mono;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ExchangeRateNotFoundException.class)
    public Mono<ErrorResponse> handleExchangeRateNotFoundException(ExchangeRateNotFoundException exception) {
        return Mono.just(
            ErrorResponse.builder(exception, HttpStatus.NOT_FOUND, exception.getMessage()).build()
        );
    }

    @ExceptionHandler(UserApiException.class)
    public Mono<ErrorResponse> handleUserApiException(UserApiException exception) {
        return Mono.just(
            ErrorResponse.builder(exception, HttpStatus.UNPROCESSABLE_ENTITY, exception.getMessage()).build()
        );
    }

    @ExceptionHandler(Exception.class)
    public Mono<ErrorResponse> handleGeneralException(Exception exception) {
        return Mono.just(
            ErrorResponse.builder(exception, HttpStatus.INTERNAL_SERVER_ERROR, exception.getMessage()).build()
        );
    }

}
