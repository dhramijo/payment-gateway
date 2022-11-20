package com.checkout.payment.repository;

import com.checkout.payment.model.Card;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {

  Optional<Card> findByNumber(String number);
}