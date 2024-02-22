package ca.ulaval.glo4002.game.domain.lawsuit;

import ca.ulaval.glo4002.game.domain.action.CharacterInteractionActionType;

public class Lawsuit {
    private final int turnNumber;
    private final String characterName;
    private final CharacterInteractionActionType actionType;
    private String lawyerName;

    public Lawsuit(int turnNumber, String characterName, CharacterInteractionActionType actionType) {
        this.turnNumber = turnNumber;
        this.characterName = characterName;
        this.actionType = actionType;
    }

    public int getTurnNumber() {
        return turnNumber;
    }

    public String getCharacterName() {
        return characterName;
    }

    public CharacterInteractionActionType getActionType() {
        return actionType;
    }

    public String getLawyerName() {
        return lawyerName;
    }

    public void setLawyerName(String lawyerName) {
        this.lawyerName = lawyerName;
    }
}
