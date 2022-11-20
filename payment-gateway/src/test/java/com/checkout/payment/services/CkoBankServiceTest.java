package com.checkout.payment.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.response.PaymentResponse;
import com.checkout.payment.restclient.CkoBankClient;
import com.checkout.payment.utils.ResourceFileLoaderUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CkoBankServiceTest {

  @Autowired
  private CkoBankService ckoBankService;

  @MockBean
  private CkoBankClient ckoBankClient;

  @Test
  void testGeneratePayment() {
    var expectedNewPaymentCreatedResponse = (PaymentResponse) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/SuccessCreatePaymentResponse.json", PaymentResponse.class);
    when(ckoBankClient.generatePayment(any())).thenReturn(expectedNewPaymentCreatedResponse);

    var paymentRequestDetails = (PaymentRequestDetails) ResourceFileLoaderUtils.loadResourceFile(
        "json/CreatePayment/CreateNewPaymentRequestPayload.json", PaymentRequestDetails.class);
    var paymentCreatedResponse = ckoBankService.generatePayment(paymentRequestDetails);

    assertThat(expectedNewPaymentCreatedResponse).isEqualTo(paymentCreatedResponse);
    verify(ckoBankClient, times(1)).generatePayment(isA(PaymentRequestDetails.class));

  }

  @Test
  void testGetPaymentDetails() {
    var expectedPaymentDetails = (PaymentResponse) ResourceFileLoaderUtils.loadResourceFile(
        "json/PaymentDetails/PaymentDetailsResponse.json", PaymentResponse.class);
    when(ckoBankClient.getPaymentDetails(anyString())).thenReturn(expectedPaymentDetails);

    var paymentDetails = ckoBankService.getPaymentDetails("pay_1");

    assertThat(expectedPaymentDetails).isEqualTo(paymentDetails);
    verify(ckoBankClient, times(1)).getPaymentDetails(isA(String.class));
  }
}