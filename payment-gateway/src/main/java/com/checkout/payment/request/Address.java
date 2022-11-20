package com.checkout.payment.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Address {
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String state;
  private String postCode;
  private String country;
}
