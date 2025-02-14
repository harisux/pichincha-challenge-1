package org.haris.chaudhrym.currencyexchange.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
    name = "Converted Amount",
    description = "Schema to converted amount and exchange rate info"
)
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ConvertAmountResponse {

    @Schema(
        description = "Origin and destination currencies",
        example = "USD/PEN"
    )
    private String originDestination;

    @Schema(
        description = "Exchange rate value",
        example = "3.71"
    )
    private Double rate;

    @Schema(
        description = "Converted amount into destination currency",
        example = "371.00"
    )
    private Double convertedAmount;
    
}
