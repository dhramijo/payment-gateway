package com.checkout.payment.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CardDetails {
  @NotNull(message = "Card number cannot be empty")
  @Size(min = 16, max = 16, message = "Invalid card number length. Please insert a 16 digit number.")
  @Pattern(regexp = "^\\d{1,16}$", message = "Invalid card number. Please insert a 16 digit number.")
  private String number;

  @NotBlank(message = "Expiry month cannot be empty")
  @Size(min = 2, max = 2, message = "Invalid expiry month length. Please insert a 2 digit number.")
  @Pattern(regexp = "^\\d{1,2}$", message = "Invalid expiry month number. Please insert a 2 digit number.")
  private String expiryMonth;

  @NotBlank(message = "Expiry year cannot be empty")
  @Size(min = 2, max = 2, message = "Invalid expiry year length. Please insert a 2 digit number.")
  @Pattern(regexp = "^\\d{1,2}$", message = "Invalid expiry year number. Please insert a 2 digit number.")
  private String expiryYear;

  @NotBlank(message = "Card holder cannot be empty")
  private String cardholder;

  @NotBlank(message = "CVV cannot be empty")
  @Size(min = 3, max = 3, message = "Invalid cvv length. Please insert a 3 digit number.")
  @Pattern(regexp = "^\\d{1,3}$", message = "Invalid cvv number. Please insert a 3 digit number.")
  private String cvv;
}
