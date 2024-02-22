package ca.ulaval.glo4002.game.domain.action;

public record CharacterInteractionGameAction(String senderID, String receiverID, CharacterInteractionActionType characterInteractionActionType, int turnNumber)
    implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.CHARACTER_TO_CHARACTER_ACTION;
    }
}
