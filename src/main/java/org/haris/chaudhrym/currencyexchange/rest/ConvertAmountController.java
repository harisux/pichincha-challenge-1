package org.haris.chaudhrym.currencyexchange.rest;

import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.service.ConvertAmountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/exchange")
@RequiredArgsConstructor
public class ConvertAmountController {

    private final ConvertAmountService convertAmountService;

    @PostMapping
    public Mono<ResponseEntity<ConvertAmountResponse>> convertExchangeAmount(@RequestBody Mono<ConvertAmountRequest> req) {
       return convertAmountService.convertExchangeAmount(req)
            .map(res -> ResponseEntity.status(HttpStatus.OK).body(res))
        ;
    }
    
}
