package org.haris.chaudhrym.currencyexchange.model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConvertAmountRequest {

    @NotNull @Positive
    private Long userId;

    @NotNull @Positive
    private Double amount;

    @NotEmpty
    private String origin;

    @NotEmpty
    private String destination;
    
}
