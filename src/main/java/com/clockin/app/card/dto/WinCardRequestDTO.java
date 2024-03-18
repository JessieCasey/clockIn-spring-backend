package com.clockin.app.card.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WinCardRequestDTO {
    String userId;
    int durationInSec;
}
