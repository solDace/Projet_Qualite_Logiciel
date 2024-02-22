package ca.ulaval.glo4002.game.interfaces.rest.dto.lawsuit;

import com.fasterxml.jackson.annotation.JsonProperty;

public record LawsuitDto(@JsonProperty("turnNumber") int turnNumber,
                         @JsonProperty("characterName") String characterName,
                         @JsonProperty("actionCode") String actionCode,
                         @JsonProperty("lawyerName") String lawyerName) {
}
