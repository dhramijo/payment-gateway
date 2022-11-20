package com.checkout.payment.restclient;

import static org.springframework.http.MediaType.APPLICATION_JSON;

import com.checkout.payment.exception.ResourceNotFoundException;
import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.response.CkoBankResponse;
import com.checkout.payment.response.PaymentResponse;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@Slf4j
@RequiredArgsConstructor
public class CkoBankClient {

  @Value("${mockserver.endpoint}")
  private String ckoBankEndpoint;

  @Value("${mockserver.api.base-path}")
  private String ckoBankApiBasePath;

  @Value("${mockserver.api.create-payments}")
  private String ckoBankCreatePaymentsApi;

  @Value("${mockserver.api.payment-details}")
  private String ckoBankGetPaymentDetails;

  private final RestTemplate restTemplate;

  public PaymentResponse getPaymentDetails(final String paymentId) {
    log.info("Getting Details for Payment Id: {}", paymentId);

    final var url = ckoBankEndpoint + ckoBankApiBasePath + ckoBankGetPaymentDetails + paymentId;
    final var headers = getHeaders();
    final var requestEntity = new HttpEntity<>(headers);

    var ckoResponse = restTemplate.exchange(
        url,
        HttpMethod.GET,
        requestEntity,
        CkoBankResponse.class
    );

    if (ckoResponse.getBody() != null) {
        return ckoResponse.getBody().getPaymentResponse();
    } else {
        throw new ResourceNotFoundException("Payment Details Response Not Found");
    }
  }

  public PaymentResponse generatePayment(final PaymentRequestDetails requestDetails) {
    log.info("Creating Payment");

    final var url = ckoBankEndpoint + ckoBankApiBasePath + ckoBankCreatePaymentsApi;
    final var headers = getHeaders();

    final var requestEntity = new HttpEntity<>(requestDetails, headers);

    var ckoResponse = restTemplate.exchange(
        url,
        HttpMethod.POST,
        requestEntity,
        CkoBankResponse.class
    );

    if (ckoResponse.getBody() != null) {
        return ckoResponse.getBody().getPaymentResponse();
    } else {
      throw new ResourceNotFoundException("Payment Creation Response Not Found");
    }
  }

  private HttpHeaders getHeaders() {
    final var headers = new HttpHeaders();
    headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
    headers.setContentType(APPLICATION_JSON);
    return headers;
  }

}
