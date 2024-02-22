package ca.ulaval.glo4002.game.interfaces.rest.dto.hamstagram;

import java.util.Collection;

import com.fasterxml.jackson.annotation.JsonProperty;

public record HamstagramDto(@JsonProperty("username") String username,
                            @JsonProperty("nbFollowers") int nbFollowers,
                            @JsonProperty("represent") Collection<String> actors,
                            @JsonProperty("representedBy") String agent) {
}
