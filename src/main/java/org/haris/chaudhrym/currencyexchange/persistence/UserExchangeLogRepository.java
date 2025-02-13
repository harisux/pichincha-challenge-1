package org.haris.chaudhrym.currencyexchange.persistence;

import org.haris.chaudhrym.currencyexchange.persistence.entity.UserExchangeLog;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserExchangeLogRepository extends ReactiveCrudRepository<UserExchangeLog, Long>  {
    
}
