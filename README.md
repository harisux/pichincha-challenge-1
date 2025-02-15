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

-----------------------------------

## Testing of solution

### Currency exchange API Testing

1. We could generate a testing token to include it in the 'authentication' header
<img src="readme-images/generate-token.png">
<img src="readme-images/include-token.png">

2. Then, pick one user from the external API
<img src="readme-images/pick-user-external-api.png">

3. Finally, we could hit the exchange API, providing the userId
<img src="readme-images/exchange-amount-happypath.png">

User log should be saved in H2 DB 'user_exchange_log' table
<img src="readme-images/user-log-saved.png">


Note: Exchange rates are preloaded in H2 table
<img src="readme-images/available-exchage-rates.png" width=200px>

4. One error case would be when exchange currency is not available
<img src="readme-images/exchange-currency-not-available.png">

5. Another error would be when user id is not found
<img src="readme-images/user-not-found.png">

6. Also if JWT token is not provided or invalid we get an unauthorized response
<img src="readme-images/token-not-provided.png">

### Swagger docs
Swagger documentation info was provided and enabled in UI.
<img src="readme-images/swagger-request-doc.png">
<img src="readme-images/swagger-response-doc.png">


### Testing coverage
1. Rest layer completely covered
<img src="readme-images/rest-layer-coverage.png">

2. Service layer has 94% coverage
<img src="readme-images/service-layer-coverage.png">

