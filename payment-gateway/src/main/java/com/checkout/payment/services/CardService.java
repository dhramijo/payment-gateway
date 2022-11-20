package com.checkout.payment.services;

import com.checkout.payment.model.Card;
import com.checkout.payment.repository.CardRepository;
import com.checkout.payment.request.CardDetails;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class CardService {

  private final CardRepository cardRepository;

  public void saveCardDetails(final CardDetails cardDetails) {
    var card = Card.builder()
        .id(generateId(cardDetails))
        .number(cardDetails.getNumber())
        .cardholder(cardDetails.getCardholder())
        .expiryMonth(cardDetails.getExpiryMonth())
        .expiryYear(cardDetails.getExpiryYear())
        .cvv(cardDetails.getCvv())
        .build();
      cardRepository.save(card);
  }

  private Integer generateId(CardDetails cardDetails) {
      return Math.abs(cardDetails.hashCode());
  }
}
