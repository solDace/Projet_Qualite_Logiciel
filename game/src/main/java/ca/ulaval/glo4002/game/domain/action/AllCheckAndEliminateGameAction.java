package ca.ulaval.glo4002.game.domain.action;

public record AllCheckAndEliminateGameAction() implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_CHECK_AND_ELIMINATE;
    }
}
