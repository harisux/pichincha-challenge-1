package org.haris.chaudhrym.currencyexchange.service.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
public class UsersApiResponse {

    private Long id;

    private String name;

    private String email;

    private String gender;

    private String status;
    
}
