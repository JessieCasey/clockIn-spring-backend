package com.clockin.app.card;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
@RequiredArgsConstructor
public class CardDataLoader {

    private final CardRepository cardRepository;

    @Value("${json.file.path}")
    private String jsonFilePath;

    @PostConstruct
    @Transactional
    public void loadCardsFromJson() {
        try {
            File file = ResourceUtils.getFile(jsonFilePath);
            String jsonContent = new String(Files.readAllBytes(file.toPath()));
            List<Card> cards = parseJson(jsonContent);

            for (Card card : cards) {
                Optional<Card> existingCard = cardRepository.findById(card.getId());
                if (existingCard.isPresent()) {
                    Card currentCard = Card.builder()
                            .id(existingCard.get().getId())
                            .name(card.getName())
                            .description(card.getDescription())
                            .rarity(card.getRarity())
                            .imageUrl(card.getImageUrl()).build();
                    cardRepository.save(currentCard);
                } else {
                    cardRepository.save(card);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private List<Card> parseJson(String jsonContent) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            return objectMapper.readValue(jsonContent, new TypeReference<>() {
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
