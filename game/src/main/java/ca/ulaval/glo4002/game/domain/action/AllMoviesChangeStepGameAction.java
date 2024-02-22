package ca.ulaval.glo4002.game.domain.action;

public record AllMoviesChangeStepGameAction() implements GameAction {

    @Override
    public GameActionType getActionType() {
        return GameActionType.ALL_MOVIES_CHANGE_STEP;
    }
}
