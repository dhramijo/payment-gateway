package com.checkout.payment.services;

import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.response.PaymentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentGatewayService {

  private final CardService cardService;
  private final CkoBankService ckoBankService;

  public PaymentResponse generatePaymentRequest(final PaymentRequestDetails requestDetails) {
    cardService.saveCardDetails(requestDetails.getCardDetails());
    return ckoBankService.generatePayment(requestDetails);
  }

  public PaymentResponse retrievePaymentDetails(final String paymentId) {
    return ckoBankService.getPaymentDetails(paymentId);
  }
}
