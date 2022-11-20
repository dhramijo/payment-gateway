package com.checkout.payment.controller;

import com.checkout.payment.request.PaymentRequestDetails;
import com.checkout.payment.response.PaymentResponse;
import com.checkout.payment.services.PaymentGatewayService;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
@Slf4j
@RequiredArgsConstructor
@Validated
public class PaymentGatewayController {
  private final PaymentGatewayService paymentGatewayService;

  @GetMapping(path = "/payments/{paymentId}")
  public ResponseEntity<PaymentResponse> retrievePaymentDetails(
      @PathVariable final String paymentId) {
    log.info("Retrieving Payment Details...");
    return ResponseEntity.ok(paymentGatewayService.retrievePaymentDetails(paymentId));
  }


  @PostMapping(path = "/payments")
  public ResponseEntity<PaymentResponse> createNewPaymentRequest(
      @Valid @RequestBody final PaymentRequestDetails requestDetails) {
    log.info("Processing new payment request, with reference: {}", requestDetails.getReference());
    return ResponseEntity.ok(paymentGatewayService.generatePaymentRequest(requestDetails));
  }

}
