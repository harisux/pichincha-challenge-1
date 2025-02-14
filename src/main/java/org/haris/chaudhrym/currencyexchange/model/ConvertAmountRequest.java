package org.haris.chaudhrym.currencyexchange.model;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConvertAmountRequest {

    @NotEmpty()
    private Long userId;

    @NotEmpty()
    private Double amount;

    @NotEmpty()
    private String origin;

    @NotEmpty()
    private String destination;
    
}
