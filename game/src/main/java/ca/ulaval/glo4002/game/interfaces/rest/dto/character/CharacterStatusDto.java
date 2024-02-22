package ca.ulaval.glo4002.game.interfaces.rest.dto.character;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CharacterStatusDto(@JsonProperty("name") String name,
                                 @JsonProperty("type") RodentType type,
                                 @JsonProperty("reputationScore") int reputationScore,
                                 @JsonProperty("bankBalance") float bankBalance,
                                 @JsonProperty("nbLawsuits") int nbLawsuits) {
}
