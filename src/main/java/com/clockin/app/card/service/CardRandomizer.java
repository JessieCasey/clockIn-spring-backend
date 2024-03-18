package com.clockin.app.card.service;

import com.clockin.app.card.Card;
import com.clockin.app.card.Card.Rarity;
import com.clockin.app.card.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class CardRandomizer {

    private final CardRepository cardRepository;
    private static final Random RANDOM = new Random();

    public Card determineCard(int durationInSec) {
        // Calculate probability based on duration
        double probability = calculateProbability(durationInSec);

        // Generate a random number between 0 and 1
        double randomValue = RANDOM.nextDouble();

        // Determine the rarity level based on the random value
        if (randomValue < probability * Rarity.EPIC.getProbability()) {
            return getRandomCardByRarity(Rarity.EPIC);
        } else if (randomValue < probability * Rarity.RARE.getProbability()) {
            return getRandomCardByRarity(Rarity.RARE);
        } else {
            return getRandomCardByRarity(Rarity.COMMON);
        }
    }

    private double calculateProbability(int durationInSec) {
        double hours = durationInSec / 3600.0;
        if (hours >= 3.0) {
            return 0.9; // Almost Full probability after 3 hours
        } else {
            return hours / 3.0;
        }
    }

    private Card getRandomCardByRarity(Rarity rarity) {
        List<Card> allByRarity = cardRepository.findAllByRarity(rarity);
        if (!allByRarity.isEmpty()) {
            int randomIndex = RANDOM.nextInt(allByRarity.size());
            return allByRarity.get(randomIndex);
        } else {
            return null;
        }
    }
}
