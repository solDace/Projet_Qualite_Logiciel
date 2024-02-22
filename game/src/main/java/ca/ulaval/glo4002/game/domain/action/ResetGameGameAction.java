package ca.ulaval.glo4002.game.domain.action;

public record ResetGameGameAction() implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.RESET_GAME;
    }

}
