package org.haris.chaudhrym.currencyexchange.service.impl;

import org.haris.chaudhrym.currencyexchange.persistence.entity.ExchangeRate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class ExchangeRateWithAmount {
    
    private ExchangeRate exchangeRate;

    private Double amount;
    
}
