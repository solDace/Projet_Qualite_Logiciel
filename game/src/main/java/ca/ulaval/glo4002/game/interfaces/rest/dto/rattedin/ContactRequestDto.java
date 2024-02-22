package ca.ulaval.glo4002.game.interfaces.rest.dto.rattedin;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ContactRequestDto(@JsonProperty("username") String username) {
}
