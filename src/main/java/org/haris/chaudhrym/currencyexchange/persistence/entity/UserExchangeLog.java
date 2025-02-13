package org.haris.chaudhrym.currencyexchange.persistence.entity;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "user_exchange_log")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UserExchangeLog {

    @Id
    @Column(value = "log_id")
    private Long logId;

    @Column(value = "user_id")
    private Long userId;

    @Column(value = "user_name")
    private String userName;

    @Column(value = "origin_amount")
    private Double origin_amount;
    
    @Column(value = "destination_amount")
    private Double destination_amount;

    @Column(value = "rate")
    private Double rate;
    
    @Column(value = "transaction_date")
    private LocalDateTime transactionDate;

}
