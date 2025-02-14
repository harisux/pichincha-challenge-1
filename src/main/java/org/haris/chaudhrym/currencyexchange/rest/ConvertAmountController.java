package org.haris.chaudhrym.currencyexchange.rest;

import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.service.ConvertAmountService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.ErrorResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Mono;

@Tag(
    name = "Exchange Currency REST API",
    description = "Operations to exchange amounts between currencies"
)
@RestController
@RequestMapping("/api/v1/exchange")
@RequiredArgsConstructor
public class ConvertAmountController {

    private final ConvertAmountService convertAmountService;

    @Operation(
        summary = "Currency amount exchange API",
        description = "It will translate an amount from the origin to destination currency"
    )
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "Currency successfully converted"
        ),
        @ApiResponse(
            responseCode = "404",
            description = "Exchange rate or user not registered",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class)
            )
        ),
        @ApiResponse(
            responseCode = "500",
            description = "Internal server error",
            content = @Content(
                schema = @Schema(implementation = ErrorResponse.class)
            )
        )
    })
    @PostMapping
    public Mono<ResponseEntity<ConvertAmountResponse>> convertExchangeAmount(@RequestBody @Valid Mono<ConvertAmountRequest> req) {
       return convertAmountService.convertExchangeAmount(req)
            .map(res -> ResponseEntity.status(HttpStatus.OK).body(res))
        ;
    }
    
}
