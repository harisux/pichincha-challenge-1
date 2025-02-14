# pichincha-challenge-1
Coding challenge to build a currency exchange REST API

## Functional requirements
- Input would be user ID, amount, origin & destination currencies
- Output would be the exchange rate and converted amount
- Validate if the user id exists and get the name from an external API
- Public services like http://gorest.co.in/public/v2/users could be integrated for user check
- For each conversation performed, it should store a log into a local DB
- Log should contain user name, amounts before & after conversion, exchange rate and date of transaction

## Technical requirements
- Stack should be Java language, Spring boot framework, Webflux reactive
- For local testing, we could use H2 DB for persistence and Postman
- Aspects to consider like exception handling and API documentation with Swagger
- Secure the endpoint using JWT based authentication (token generation not considered)
- Unit testing of rest and service layers


