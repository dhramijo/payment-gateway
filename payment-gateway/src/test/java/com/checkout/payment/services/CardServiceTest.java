package com.checkout.payment.services;

import static org.mockito.Mockito.when;

import com.checkout.payment.model.Card;
import com.checkout.payment.repository.CardRepository;
import com.checkout.payment.request.CardDetails;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class CardServiceTest {

  @Autowired private CardService cardService;

  @MockBean private CardRepository cardRepository;

  @Test
  void saveCardDetails() {
    var card = Card.builder()
        .id(44350992)
        .number("4098870511026095")
        .cardholder("Bruce Wayne")
        .expiryMonth("01")
        .expiryYear("24")
        .cvv("123")
        .build();
    when(cardRepository.save(card)).thenReturn(card);

    var cardDetails = CardDetails.builder()
        .number("4098870511026095")
        .cardholder("Bruce Wayne")
        .expiryMonth("01")
        .expiryYear("24")
        .cvv("123")
        .build();
    cardService.saveCardDetails(cardDetails);
  }
}