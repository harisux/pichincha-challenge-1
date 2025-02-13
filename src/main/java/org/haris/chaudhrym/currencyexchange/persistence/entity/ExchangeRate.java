package org.haris.chaudhrym.currencyexchange.persistence.entity;

import java.math.BigDecimal;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "exchange_rate")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class ExchangeRate {

    @Id
    @Column(value = "rate_id")
    private Long rateId;

    @Column(value = "origin")
    private String origin;

    @Column(value = "destination")
    private String destination;

    @Column(value = "rate")
    private BigDecimal rate;
    
}
