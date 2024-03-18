package com.clockin.app.card;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "cards")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Card {

    @Id
    String id; // Unique identifier for the card

    String name; // Name of the card

    String description; // Description of the card's function or lore

    Rarity rarity; // Enum representing the card's rarity level (e.g., COMMON, RARE, EPIC, LEGENDARY)

    String imageUrl; // URL for the card's image stored in the S3 bucket

    @Getter
    public enum Rarity {
        COMMON(0.7),
        RARE(0.2),
        EPIC(0.1);

        private final double probability;

        Rarity(double probability) {
            this.probability = probability;
        }
    }
}


