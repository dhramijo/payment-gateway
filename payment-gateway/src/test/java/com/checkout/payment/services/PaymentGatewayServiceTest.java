package com.checkout.payment.services;


import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.checkout.payment.request.CardDetails;
import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.response.PaymentResponse;
import com.checkout.payment.utils.ResourceFileLoaderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class PaymentGatewayServiceTest {

  @MockBean private CardService cardService;

  @MockBean private CkoBankService ckoBankService;

  @Autowired private PaymentGatewayService paymentGatewayService;

  @Test
  void testGeneratePaymentRequest() {
    var expectedNewPaymentCreatedResponse = (PaymentResponse) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/SuccessCreatePaymentResponse.json", PaymentResponse.class);
    when(ckoBankService.generatePayment(any())).thenReturn(expectedNewPaymentCreatedResponse);

    var paymentRequestDetails = (PaymentRequestDetails) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/CreateNewPaymentRequestPayload.json", PaymentRequestDetails.class);

    var paymentCreatedResponse = paymentGatewayService.generatePaymentRequest(paymentRequestDetails);

    assertThat(expectedNewPaymentCreatedResponse).isEqualTo(paymentCreatedResponse);
    verify(cardService, times(1)).saveCardDetails(isA(CardDetails.class));
    verify(ckoBankService, times(1)).generatePayment(isA(PaymentRequestDetails.class));
  }

  @Test
  void testRetrievePaymentDetails() {
    var expectedPaymentResponse = (PaymentResponse) ResourceFileLoaderUtils.loadResourceFile(
        "json/PaymentDetails/PaymentDetailsResponse.json", PaymentResponse.class);
    when(ckoBankService.getPaymentDetails(anyString())).thenReturn(expectedPaymentResponse);

    var paymentDetails = paymentGatewayService.retrievePaymentDetails("pay_1");

    assertThat(expectedPaymentResponse).isEqualTo(paymentDetails);
    verify(ckoBankService, times(1)).getPaymentDetails(isA(String.class));
  }
}