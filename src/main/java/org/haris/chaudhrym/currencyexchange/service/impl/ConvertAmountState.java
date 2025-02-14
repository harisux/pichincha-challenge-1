package org.haris.chaudhrym.currencyexchange.service.impl;

import org.haris.chaudhrym.currencyexchange.model.ConvertAmountResponse;
import org.haris.chaudhrym.currencyexchange.persistence.entity.ExchangeRate;
import org.haris.chaudhrym.currencyexchange.persistence.entity.UserExchangeLog;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * POJO to store the progressive state of exchange conversion process
 */
@Getter @Setter @AllArgsConstructor @NoArgsConstructor @Builder(toBuilder = true)
public class ConvertAmountState {

    private Long userId;

    private String userName;
    
    private ExchangeRate exchangeRate;

    private Double amount;

    private ConvertAmountResponse convertedResponse;

    private UserExchangeLog userLog;

}
