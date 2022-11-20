package com.checkout.payment.request;

import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDetails {
  @Valid
  private CardDetails cardDetails;
  private Customer customer;
  private int amount;
  private String currency;
  private String reference;
}
