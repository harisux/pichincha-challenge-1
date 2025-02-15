package org.haris.chaudhrym.currencyexchange.service.impl;

import org.haris.chaudhrym.currencyexchange.config.WebClientConfig;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountRequest;
import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.persistence.ExchangeRateRepository;
import org.haris.chaudhrym.currencyexchange.persistence.UserExchangeLogRepository;
import org.haris.chaudhrym.currencyexchange.persistence.entity.ExchangeRate;
import org.haris.chaudhrym.currencyexchange.persistence.entity.UserExchangeLog;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.reactive.function.client.WebClient;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

import static org.assertj.core.api.Assertions.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import reactor.core.publisher.Mono;

@SpringBootTest(classes = { ConvertAmountServiceImpl.class, WebClientConfig.class })
public class ConvertAmountServiceImplTest {

    @MockitoBean
    private ExchangeRateRepository exchangeRateRepository;

    @MockitoBean
    private UserExchangeLogRepository userLogRepository;

    @Autowired
    private WebClient webClient;

    @Autowired
    private ConvertAmountServiceImpl convertAmountServiceImpl;

    public static MockWebServer mockUsersApi;

    @BeforeAll
    public static void setUp() throws IOException {
        mockUsersApi = new MockWebServer();
        mockUsersApi.start();
    }

    @AfterAll
    public static void tearDown() throws IOException {
        mockUsersApi.shutdown();
    }

    @Test
    public void shouldConvertExchangeAmount() throws JsonProcessingException {
        //Given 
        ConvertAmountRequest request = new ConvertAmountRequest();
        request.setUserId(123L);
        request.setAmount(100.00);
        request.setOrigin("USD");
        request.setDestination("PEN");

        //Mock exchange rate repo
        ExchangeRate testExchangeRate = 
            new ExchangeRate(1L, "USD", "PEN", BigDecimal.valueOf(3.71));
        Mockito.when(exchangeRateRepository.findByOriginAndDestination("USD", "PEN"))
            .thenReturn(Mono.just(testExchangeRate));

        //Mock user logs repo
        UserExchangeLog testUserLog = new UserExchangeLog(
            456L, 123L, "user123", 100.00, 371.00, 3.71, LocalDateTime.now());
        Mockito.when(userLogRepository.save(Mockito.any())).thenReturn(Mono.just(testUserLog));

        //Setup mocked users api
        String mockUsersApiBase = String.format("http://localhost:%s", mockUsersApi.getPort());
        convertAmountServiceImpl.setUsersBaseUrl(mockUsersApiBase);

        UsersApiResponse testUserResponse = new UsersApiResponse(
            123L, "user123", "test@mail.com", "male", "active");
        mockUsersApi.enqueue(new MockResponse()
            .setBody(new ObjectMapper().writeValueAsString(List.of(testUserResponse)))
            .addHeader("Content-Type", "application/json"));

        //When
        ConvertAmountResponse response 
                = convertAmountServiceImpl.convertExchangeAmount(Mono.just(request)).block();

        //Then
        assertThat(response)
            .isNotNull()
            .hasFieldOrPropertyWithValue("originDestination", "USD/PEN")
            .hasFieldOrPropertyWithValue("rate", 3.71)
            .hasFieldOrPropertyWithValue("convertedAmount", 371.0)
        ;
    }
    
}
