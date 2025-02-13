CREATE TABLE exchange_rate(
    rate_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    origin VARCHAR(10) NOT NULL,
    destination VARCHAR(10) NOT NULL,
    rate NUMERIC(20, 4) NOT NULL
);