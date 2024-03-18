package com.clockin.app.card.service;

import com.clockin.app.card.Card;
import com.clockin.app.card.dto.CardResponseWrapperDTO;

import java.util.List;

public interface CardService {
    Card determineCard(int durationInSec);

    void saveCardForUser(String userId, Card card);

    List<Card> getAllCards(String rarity);

    CardResponseWrapperDTO mapCardsFoundByUser(List<Card> cards, String userId);

}
