CREATE TABLE exchange_rate(
    rate_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    origin VARCHAR(10) NOT NULL,
    destination VARCHAR(10) NOT NULL,
    rate NUMERIC(20, 4) NOT NULL
);

CREATE TABLE user_exchange_log(
    log_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    user_name VARCHAR(50) NOT NULL,
    origin_amount NUMERIC(20, 4) NOT NULL,
    destination_amount NUMERIC(20, 4) NOT NULL,
    rate NUMERIC(20, 4) NOT NULL,
    transaction_date DATE NOT NULL
);