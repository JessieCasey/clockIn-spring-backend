package com.clockin.app.card;

import com.clockin.app.card.dto.CardResponseDTO;
import com.clockin.app.card.dto.CardResponseWrapperDTO;
import com.clockin.app.card.dto.WinCardRequestDTO;
import com.clockin.app.card.service.CardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@CrossOrigin("*")
@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;

    @GetMapping
    public ResponseEntity<List<Card>> getAllCards(@RequestParam(value = "ALL", required = false) String rarity) {
        List<Card> cards = cardService.getAllCards(rarity);
        return ResponseEntity.status(HttpStatus.OK).body(cards);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<CardResponseWrapperDTO> getAllCardsWithUserFound(@PathVariable String userId,
                                                                           @RequestParam String rarity) {
        List<Card> cards = cardService.getAllCards(rarity);
        CardResponseWrapperDTO responseWrapperDTO = cardService.mapCardsFoundByUser(cards, userId);

        return ResponseEntity.status(HttpStatus.OK).body(responseWrapperDTO);
    }

    @PostMapping("/win")
    public ResponseEntity<?> winCard(@RequestBody WinCardRequestDTO request) {
        Card card = cardService.determineCard(request.getDurationInSec());
        if (card == null) {
            return ResponseEntity.status(HttpStatus.OK).body("Not enough time to get a card, you need to have at least 30 minutes");
        }
        cardService.saveCardForUser(request.getUserId(), card);
        CardResponseDTO build = CardResponseDTO.builder()
                .id(card.getId())
                .name(card.getName())
                .description(card.getDescription())
                .imageUrl(card.getImageUrl())
                .foundByUser(true)
                .rarity(card.getRarity()).build();
        return ResponseEntity.status(HttpStatus.CREATED).body(build);
    }

}
