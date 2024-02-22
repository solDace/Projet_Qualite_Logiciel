package ca.ulaval.glo4002.game.interfaces.rest.dto.movie;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record MovieDto(@JsonProperty("title") String title,
                       @JsonProperty("type") String type,
                       @JsonProperty("potentialCasting") Collection<String> potentialCasting,
                       @JsonProperty("casting") Collection<String> casting,
                       @JsonProperty("boxOffice") int boxOffice) {
}
