package com.clockin.app.card.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class CardResponseWrapperDTO {
    List<CardResponseDTO> cards;
    int totalAmount;
    int userFoundAmount;
}
