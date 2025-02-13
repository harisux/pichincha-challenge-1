package org.haris.chaudhrym.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConvertAmountResponse {

    private String originDestination;

    private Double rate;

    private Double convertedAmount;
    
}
