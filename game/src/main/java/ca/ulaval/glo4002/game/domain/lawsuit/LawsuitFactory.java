package ca.ulaval.glo4002.game.domain.lawsuit;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;

public class LawsuitFactory {
    public Lawsuit createLawsuit(int turnNumber, String characterName, CharacterInteractionActionType actionType) {
        return new Lawsuit(turnNumber, characterName, actionType);
    }
}
