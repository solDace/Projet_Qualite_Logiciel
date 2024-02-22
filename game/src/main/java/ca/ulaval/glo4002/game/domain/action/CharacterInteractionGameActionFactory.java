package ca.ulaval.glo4002.game.domain.action;

public class CharacterInteractionGameActionFactory {
    public CharacterInteractionGameAction createCharacterInteractionGameAction(String senderID,
                                                                               String receiverID,
                                                                               CharacterInteractionActionType actionType,
                                                                               int turnNumber) {
        return new CharacterInteractionGameAction(senderID, receiverID, actionType, turnNumber);
    }
}
