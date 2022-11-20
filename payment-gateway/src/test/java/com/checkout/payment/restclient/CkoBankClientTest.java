package com.checkout.payment.restclient;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

import com.checkout.payment.exception.ResourceNotFoundException;
import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.response.CkoBankResponse;
import com.checkout.payment.utils.ResourceFileLoaderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@SpringBootTest
class CkoBankClientTest {

  @MockBean
  private RestTemplate restTemplate;

  @Autowired
  private CkoBankClient ckoBankClient;

  @Test
  void testGetPaymentDetails() {
    var ckoBankResponse = (CkoBankResponse) ResourceFileLoaderUtils.loadResourceFile(
        "json/PaymentDetails/CkoBankPaymentDetailsResponse.json", CkoBankResponse.class);

    var expectedPaymentDetails = ckoBankResponse.getPaymentResponse();

    when(restTemplate.exchange(anyString(), any(), any(), eq(CkoBankResponse.class)))
        .thenReturn(new ResponseEntity<>(ckoBankResponse, HttpStatus.OK));

    var paymentDetails = ckoBankClient.getPaymentDetails("pay_1");

    assertThat(expectedPaymentDetails).isEqualTo(paymentDetails);
  }

  @Test
  void testGetPaymentDetailsThrowsException() {
    when(restTemplate.exchange(anyString(), any(), any(), eq(CkoBankResponse.class)))
        .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

    assertThrows(ResourceNotFoundException.class, () -> ckoBankClient.getPaymentDetails("pay_1"));
  }

  @Test
  void testGeneratePayment() {
    var ckoBankResponse = (CkoBankResponse) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/CkoBankSuccessCreatePaymentResponse.json", CkoBankResponse.class);

    var expectedPaymentCreatedResponse = ckoBankResponse.getPaymentResponse();

    when(restTemplate.exchange(anyString(), any(), any(), eq(CkoBankResponse.class)))
        .thenReturn(new ResponseEntity<>(ckoBankResponse, HttpStatus.OK));

    var paymentRequestDetails = (PaymentRequestDetails) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/CreateNewPaymentRequestPayload.json", PaymentRequestDetails.class);
    var paymentDetails = ckoBankClient.generatePayment(paymentRequestDetails);

    assertThat(expectedPaymentCreatedResponse).isEqualTo(paymentDetails);
  }

  @Test
  void testGeneratePaymentThrowsException() {
    when(restTemplate.exchange(anyString(), any(), any(), eq(CkoBankResponse.class)))
        .thenReturn(new ResponseEntity<>(null, HttpStatus.OK));

    var paymentRequestDetails = (PaymentRequestDetails) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/CreateNewPaymentRequestPayload.json", PaymentRequestDetails.class);
    assertThrows(ResourceNotFoundException.class, () -> ckoBankClient.generatePayment(paymentRequestDetails));
  }

}