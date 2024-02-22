package ca.ulaval.glo4002.game.interfaces.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ErrorDto(@JsonProperty("error")
                       String error,
                       @JsonProperty("description")
                       String description) {
}
