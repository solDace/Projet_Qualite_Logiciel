package ca.ulaval.glo4002.game.interfaces.rest.dto.turn;

import com.fasterxml.jackson.annotation.JsonProperty;

public record TurnDto(@JsonProperty("turnNumber") int turnNumber) {
}
