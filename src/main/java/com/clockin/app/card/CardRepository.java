package com.clockin.app.card;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CardRepository  extends MongoRepository<Card, String> {
    List<Card> findAllByRarity(Card.Rarity rarity);
}
