package org.haris.chaudhrym.currencyexchange.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConvertAmountRequest {

    private Long userId;

    private Double amount;

    private String origin;

    private String destination;
    
}
