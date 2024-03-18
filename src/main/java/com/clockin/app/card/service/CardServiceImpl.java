package com.clockin.app.card.service;

import com.clockin.app.card.Card;
import com.clockin.app.card.CardRepository;
import com.clockin.app.card.dto.CardResponseDTO;
import com.clockin.app.card.dto.CardResponseWrapperDTO;
import com.clockin.app.common.mapper.CardMapper;
import com.clockin.app.user.User;
import com.clockin.app.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardRepository cardRepository;
    private final UserService userService;
    private final CardMapper cardMapper;
    private final CardRandomizer cardRandomizer;

    @Override
    public List<Card> getAllCards(String rarity) {
        if (rarityContains(rarity)) {
            return cardRepository.findAllByRarity(Card.Rarity.valueOf(rarity));
        } else {
            return cardRepository.findAll();
        }
    }

    private static boolean rarityContains(String test) {
        for (Card.Rarity c : Card.Rarity.values()) {
            if (c.name().equals(test)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public CardResponseWrapperDTO mapCardsFoundByUser(List<Card> cards, String userId) {
        User user = userService.getUserById(userId);
        List<CardResponseDTO> cardResponses = new ArrayList<>(cards.size());

        Set<String> userCardIds = user != null && user.getCards() != null
                ? user.getCards().stream().map(Card::getId).collect(Collectors.toSet())
                : Collections.emptySet();

        int userFoundAmount = 0;
        for (Card card : cards) {
            CardResponseDTO cardResponse = cardMapper.cardToCardResponseDTO(card);
            boolean foundByUser = userCardIds.contains(card.getId());
            if (foundByUser) userFoundAmount++;
            cardResponse.setFoundByUser(foundByUser);
            cardResponses.add(cardResponse);
        }

        CardResponseWrapperDTO response = new CardResponseWrapperDTO();
        response.setCards(cardResponses);
        response.setUserFoundAmount(userFoundAmount);
        response.setTotalAmount(cardResponses.size());

        return response;
    }


    @Override
    public Card determineCard(int durationInSec) {
        double hours = durationInSec / 3600.0;
        if (hours > 0.0) {
            return cardRandomizer.determineCard(durationInSec);
        } else {
            return null;
        }

    }

    @Override
    public void saveCardForUser(String userId, Card card) {
        User user = userService.getUserById(userId);
        if (user.getCards() == null) {
            user.setCards(new ArrayList<>(List.of(card)));
        } else {
            user.getCards().add(card);
        }
        userService.saveUser(user);
    }
}
