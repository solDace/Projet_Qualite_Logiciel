package ca.ulaval.glo4002.game.interfaces.rest.dto.action;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterActionCreationDto(@JsonProperty("from") String from,
                                         @JsonProperty("to") String to,
                                         @JsonProperty("actionCode") String actionCode) {

}
