package org.haris.chaudhrym.currencyexchange.test;

import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.rest.ConvertAmountController;
import org.haris.chaudhrym.currencyexchange.service.ConvertAmountService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.*;

import reactor.core.publisher.Mono;

@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ConvertAmountController.class, 
    excludeAutoConfiguration = { ReactiveSecurityAutoConfiguration.class })
public class ConvertAmountControllerTest {

    @MockitoBean
    ConvertAmountService convertAmountService;

    @Autowired
    private WebTestClient webTestClient;

    @Test
    public void shouldConvertExchangeAmount() {
        //Given
        ConvertAmountRequest request = new ConvertAmountRequest();
        request.setUserId(Long.valueOf(1234));
        request.setAmount(123.123);
        request.setOrigin("USD");
        request.setDestination("PEN");

        ConvertAmountResponse testResponse = new ConvertAmountResponse();
        Double testAmount = 151.441; testResponse.setConvertedAmount(testAmount);
        String testOriginDest = "USD/PEN"; testResponse.setOriginDestination(testOriginDest);
        Double testRate = 1.23; testResponse.setRate(testRate);

        Mockito.when(convertAmountService.convertExchangeAmount(Mockito.any()))
            .thenReturn(Mono.just(testResponse));

        //When
        webTestClient.post()
            .uri("/api/v1/exchange")
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(request), ConvertAmountRequest.class)
            .exchange()
        //Then
            .expectStatus().isOk()
            .expectBody(ConvertAmountResponse.class)
            .consumeWith(res -> {
                assertThat(res.getResponseBody()).isNotNull()
                    .hasFieldOrPropertyWithValue("convertedAmount", testAmount)
                    .hasFieldOrPropertyWithValue("rate", testRate)
                    .hasFieldOrPropertyWithValue("originDestination", testOriginDest);
            })
        ;
    }
    
}
