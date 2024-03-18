package com.clockin.app.card.dto;

import com.clockin.app.card.Card;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

@Getter
@Setter
@Builder
public class CardResponseDTO {

    String id;
    String name;
    String description;
    Card.Rarity rarity;
    String imageUrl;
    boolean foundByUser;

}
