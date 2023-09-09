package com.ap.homebanking.services;

import com.ap.homebanking.dto.CardDTO;
import com.ap.homebanking.models.Card;

import java.util.Set;

public interface CardService {
    public Set<CardDTO> getCards();
    boolean existsCardByNumber(String cardNumber);
    void saveCard(Card card);
}
