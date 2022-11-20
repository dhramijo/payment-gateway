package com.checkout.payment.response;

import com.checkout.payment.request.Customer;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class PaymentResponse {
  private String paymentId;
  private int amount;
  private String currency;
  private boolean approved;
  private String status;
  private String responseSummary;
  private String processedOn;
  private CardPaymentDetails cardDetails;
  private Customer customer;
}
