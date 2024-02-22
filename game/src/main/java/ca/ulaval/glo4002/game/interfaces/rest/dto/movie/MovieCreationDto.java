package ca.ulaval.glo4002.game.interfaces.rest.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieCreationDto(@JsonProperty("title") String title,
                               @JsonProperty("type") String type) {
}
