package ca.ulaval.glo4002.game.interfaces.rest.dto.rattedin;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RattedInDto(@JsonProperty("username") String username,
                          @JsonProperty("status") String status,
                          @JsonProperty("contacts") Collection<String> contacts) {
}
