package com.checkout.payment.services;

import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.response.PaymentResponse;
import com.checkout.payment.restclient.CkoBankClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CkoBankService {

private final CkoBankClient ckoBankClient;
  public PaymentResponse generatePayment(final PaymentRequestDetails requestDetails) {
    return ckoBankClient.generatePayment(requestDetails);
  }


  public PaymentResponse getPaymentDetails(final String paymentId) {
    return ckoBankClient.getPaymentDetails(paymentId);
  }
}
