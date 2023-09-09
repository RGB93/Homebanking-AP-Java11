package com.ap.homebanking.services.implement;

import com.ap.homebanking.dto.CardDTO;
import com.ap.homebanking.models.Card;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.services.CardService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

public class CardServiceImplement implements CardService {

    @Autowired
    private CardRepository cardRepository;

    @Override
    public Set<CardDTO> getCards() {
        return null;
    }

    @Override
    public boolean existsCardByNumber(String cardNumber) {
        return false;
    }

    @Override
    public void saveCard(Card card) {

    }
}
