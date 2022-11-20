package com.checkout.payment.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Card {

  @Id
  private Integer id;
  private String number;
  private String expiryMonth;
  private String expiryYear;
  private String cardholder;
  private String cvv;

}
