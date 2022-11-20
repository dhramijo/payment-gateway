# Checkout.com - Payment Gateway

## The Challenge
E-Commerce is experiencing exponential growth and merchants who sell their goods or services online need a way to easily collect money from their customers.
We would like to build a payment gateway, an API based application that will allow a merchant to offer a way for their shoppers to pay for their product.
Processing a payment online involves multiple steps and entities:

1. **Shopper**: Individual who is buying the product online.
2. **Merchant**: The seller of the product. For example, Apple or Amazon.
3. **Payment Gateway**: Responsible for validating requests, storing card information and forwarding payment requests and accepting payment responses to and from the acquiring bank.
4. **Acquiring Bank**: Allows us to do the actual retrieval of money from the shopper’s card and payout to the merchant. It also performs some validation of the card information and then sends the payment details to the appropriate 3rd party organization for processing.

We will be building the payment gateway only and simulating the acquiring bank component in order to allow us to fully test the payment flow.

## Requirements
The product requirements for this initial phase are the following:
1. A merchant should be able to process a payment through the payment gateway and receive either
   a successful or unsuccessful response.
2. A merchant should be able to retrieve the details of a previously made payment. The next section
   will discuss each of these in more detail.


## Approach/Assumptions:

**CKO bank simulator** has been implemented via a **Mockserver** by creating 3 expectations  
(refer to *initializerJson.json* file in *cko-bank-simulator* folder):

* Return Payment Details.
    * This will be returned always, every time you call this endpoint
* Return Successful response for a process payment, with few payment details.
    * Set to return success for 3 consecutive Post calls
* Return Unsuccessful response for a process payment, with error details.
    * Set to return unsuccess for 3-5 consecutive Post calls, after the success calls, then will return **404**

To store **card** information, a card table has been created in a *mysql* database, saving the following information:

* id
* cardholder name
* card number
* cvv
* expiry month
* expiry year

Validation checks are implemented on the card details received for the payment:

* **Card Number**
    * cannot be empty
    * 16 digit number
* **Cardholder**
    * cannot be empty
* **Cvv**
    * cannot be empty
    * 3 digit number
* **Expiry Month**
    * cannot be empty
    * 2 digit number
* **Expiry Year**
    * cannot be empty
    * 2 digit number


## API Flow

#### Process New Payment Request

* Save the card details in payment-gateway database (schema is called **checkout**)
* Forward request to cko-bank-simulator:
    * Post call to ```localhost:1080/api/v1/payments```
    * Return success payload with few payment details

      ```json    
          {
              "data": {
                  "paymentId": "pay_1",
                  "amount": 6540,
                  "currency": "GBP",
                  "approved": true,
                  "reference": "ORD-123",
                  "status": "Authorized",
                  "responseSummary": "Approved",
                  "processedOn": "2022-11-18T10:11:12Z"
              }
          }
      ```  
    * **Payment Gateway Endpoint** called  ```http://localhost:8080/api/v1/payments```

#### Retrieve Payment Details

* Forward request to cko-bank-simulator:
  * Get call to ```localhost:1080/api/v1/payments/pay_1```
  * Return success payload with  payment details
     ```json    
     {
         "data": {
             "paymentId": "pay_1",
             "amount": 6540,
             "currency": "GBP",
             "approved": true,
             "reference": "ORD-123",
             "status": "Authorized",
             "responseSummary": "Approved",
             "processedOn": "2022-11-18T10:11:12Z",
             "cardDetails": {
                 "maskedNr": "409887******6094",
                 "expiryMonth": "01",
                 "expiryYear": "24",
                 "cardholder": "Bruce Wayne"
             },
             "customer": {
                 "firstName": "Bruce",
                 "lastName": "Wayne",
                 "email": "brucewayne@gmail.com",
                 "phone": "+444155552671",
                 "address": {
                     "addressLine1": "BW LTD",
                     "addressLine2": "90 St James Road",
                     "postCode": "E14",
                     "city": "London",
                     "state": "London",
                     "country": "GB"
                 }
             }
         }
     }
     ```  


* **Payment Gateway Endpoint** called  ```http://localhost:8080/api/v1/payments/{paymentId}```


## Tech Used

For this project I have used:

- Spring Boot
- Java 11
- Spring Web
- Lombok
- Docker
- Swagger for OpenAPI specs
- MySql for storing card information in Payment Gateway
- Mockserver as CKO Bank Simulator

## Running the application

To start the application, open the terminal from the root folder run the following command:

```sh
docker-compose up -d --build
```
### API Data Test
* **Process New Payment Request**

    * POST request

      ```sh
      curl --location --request POST 'localhost:8080/api/v1/payments' \
      --header 'Content-Type: application/json' \
      --data-raw '{
        "amount": 6540,
        "currency": "GBP",
        "reference": "ORD-1223",
        "cardDetails": {
          "number": "4098870511026094",
          "expiryMonth": "03",
          "expiryYear": "26",
          "cardholder": "Bruce Wayne",
          "cvv": "112"
        },
        "customer": {
          "firstName": "Bruce",
          "lastName": "Wayne",
          "email": "brucewayne@gmail.com",
          "phone": "+444155552671",
          "address": {
            "addressLine1": "BW LTD",
            "addressLine2": "90 St James Road",
            "postCode": "E14",
            "city": "London",
            "state": "London",
            "country": "GB"
          }
        }
      }'
      ```

    * Success Response

      ```json             
      {
          "paymentId": "pay_1",
          "amount": 6540,
          "currency": "GBP",
          "approved": true,
          "reference": "ORD-123",
          "status": "Authorized",
          "responseSummary": "Approved",
          "processedOn": "2022-11-18T10:11:12Z"
      }
      ```

* **Retrieve Payment Details**

    * GET Request

      ```sh
      curl --location --request GET 'localhost:8080/api/v1/payments/pay_1'
      ```

    * Success Response

      ```json
      {
          "paymentId": "pay_1",
          "amount": 6540,
          "currency": "GBP",
          "approved": true,
          "status": "Authorized",
          "responseSummary": "Approved",
          "processedOn": "2022-11-18T10:11:12Z",
          "cardDetails": {
              "maskedNr": "409887******6094",
              "expiryMonth": "01",
              "expiryYear": "24",
              "cardholder": "Bruce Wayne"
          },
          "customer": {
              "firstName": "Bruce",
              "lastName": "Wayne",
              "address": {
                  "addressLine1": "BW LTD",
                  "addressLine2": "90 St James Road",
                  "city": "London",
                  "state": "London",
                  "postCode": "E14",
                  "country": "GB"
              },
              "email": "brucewayne@gmail.com",
              "phone": "+444155552671"
          }
      }
      ```

* **Retrieve Stored Card Details**

  From the root folder run:

  ```sh
  mysql -h 127.0.0.1 --port 3306 -u admin -padmin checkout < mysql/sql-query/get_card_info.sql
  ```

## Swagger - Open API Spec

After docker compose command, once it is completed successfully, you can access below endpoints:

- Swagger Endpoint: Available at [http://localhost:80](http://localhost:80)
- API Endpoints: 
    - Process Payment Request
        - [http://localhost:8080/api/v1/payments](http://localhost:8080/api/v1/payments)
    - Retrieve Payment Details 
        - [http://localhost:8080/api/v1/payments/pay_1](http://localhost:8080/api/v1/payments/pay_1)

