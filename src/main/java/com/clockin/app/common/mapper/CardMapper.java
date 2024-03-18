package com.clockin.app.common.mapper;

import com.clockin.app.card.Card;
import com.clockin.app.card.dto.CardResponseDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CardMapper {
    CardResponseDTO cardToCardResponseDTO(Card card);
}
