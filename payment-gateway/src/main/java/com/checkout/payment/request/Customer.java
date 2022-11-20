package com.checkout.payment.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
  private String firstName;
  private String lastName;
  private Address address;
  private String email;
  private String phone;
}
