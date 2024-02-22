package ca.ulaval.glo4002.game.interfaces.rest.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterCreationDto(@JsonProperty("name") String name,
                                   @JsonProperty("type") String type,
                                   @JsonProperty("salary") int salary) {
}
