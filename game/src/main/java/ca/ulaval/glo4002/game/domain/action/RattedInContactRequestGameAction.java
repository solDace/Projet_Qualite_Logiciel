package ca.ulaval.glo4002.game.domain.action;

public record RattedInContactRequestGameAction(String receiverUsername, String requesterUsername) implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.RATTEDIN_CONTACT_REQUEST;
    }
}
