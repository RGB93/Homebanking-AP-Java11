package com.ap.homebanking.controllers;

import com.ap.homebanking.models.Card;
import com.ap.homebanking.models.CardColor;
import com.ap.homebanking.models.CardType;
import com.ap.homebanking.models.Client;
import com.ap.homebanking.repositories.CardRepository;
import com.ap.homebanking.repositories.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class CardController {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private ClientRepository clientRepository;
    @RequestMapping(path = "client/current/cards", method = RequestMethod.POST)
    public ResponseEntity<Object> register(Authentication authentication, @RequestParam CardType cardType, @RequestParam CardColor cardColor) {
        Client client = clientRepository.findByEmail(authentication.getName());
        if(cardType == null) {
            return new ResponseEntity<>("You have to choose a card type", HttpStatus.FORBIDDEN);
        }
        if(cardColor == null) {
            return new ResponseEntity<>("You have to choose a card color", HttpStatus.FORBIDDEN);
        }
        Set<Card> cardsType = client.getCards().stream().filter(card -> card.getType() == cardType).collect(Collectors.toSet());
        boolean notRepeatTypeAndColor = cardsType.stream().anyMatch(card -> card.getColor() == cardColor);
        if (notRepeatTypeAndColor) {
            return new ResponseEntity<>("You already have one " + cardColor + " " + cardType + " card.", HttpStatus.FORBIDDEN);
        }

        if (cardsType.size() >= 3) {
            return new ResponseEntity<>("You already have 3 or more of this type (" + cardType + ") of cards.", HttpStatus.FORBIDDEN);
        }
        int number1 = (int) ((Math.random() * (10000 - 1000)) + 1000);
        int number2 = (int) ((Math.random() * (10000 - 1000)) + 1000);
        int number3 = (int) ((Math.random() * (10000 - 1000)) + 1000);
        int number4 = (int) ((Math.random() * (10000 - 1000)) + 1000);

        String cardHolder = client.getLastName() + " " + client.getFirstName();
        String number = number1 + "-" + number2 + "-" + number3 + "-" + number4;
        short cvv = (short) ((Math.random() * (1000 - 100)) + 100);
        LocalDate thruDate = LocalDate.now();
        LocalDate fromDate = LocalDate.now().plusYears(5);

        Card card = new Card(cardHolder, cardType, cardColor, number, cvv, thruDate, fromDate);

        client.addCard(card);
        cardRepository.save(card);

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
